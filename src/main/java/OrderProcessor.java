import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderProcessor {
    public static Map<String, List<ProductOutput>> process(File inputFile) throws IOException {
        Map<String, List<ProductOutput>> categorizedProducts = new HashMap<>();
        XmlMapper xmlMapper = new XmlMapper();
        String xml = inputStreamToString(new FileInputStream(inputFile));
        Orders orders = xmlMapper.readValue(xml, Orders.class);

        // get the order ID from inputFile
        String orderNumber = inputFile.getName().replace("orders", "").replace(".xml", "");
        for (Order currentOrder : orders.getOrders()) {
            for (Product currentProduct : currentOrder.getProducts()) {
                ProductOutput newObj = new ProductOutput(currentProduct.getDescription(), currentProduct.getGtin(), currentProduct.getPrice(), currentOrder.getID());
                List<ProductOutput> productOutputs = categorizedProducts.get(currentProduct.getSupplier() + orderNumber);
                if (productOutputs == null) {
                    List<ProductOutput> newList = new ArrayList<>();
                    newList.add(newObj);
                    categorizedProducts.put(currentProduct.getSupplier() + orderNumber, newList);
                } else {
                    productOutputs.add(newObj);
                }
            }
        }
        // TODO sort supplier output list by timestamp descendant and price
        return categorizedProducts;
    }

    private static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

}
