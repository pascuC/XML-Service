import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderProcessor {
    public static Map<String, List<SupplierOutput>> process(File inputFile) throws IOException {
        Map<String, List<SupplierOutput>> categorizedProducts = new HashMap<>();
        XmlMapper xmlMapper = new XmlMapper();
        String xml = inputStreamToString(new FileInputStream(inputFile));
        Orders orders = xmlMapper.readValue(xml, Orders.class);
        List<SupplierOutput> supplierProducts = new ArrayList<>();

        // get the order ID
        String orderNumber = inputFile.getName().replace("order", "").replace(".xml", "");
        for (Order currentOrder : orders.getOrders()) {
            for (Product currentProduct : currentOrder.getProducts()) {
                SupplierOutput newObj = new SupplierOutput(currentProduct.getDescription(), currentProduct.getGtin(), currentProduct.getPrice(), currentOrder.getID());
                List<SupplierOutput> supplierOutputs = categorizedProducts.get(currentProduct.getSupplier() + orderNumber);
                if (supplierOutputs == null) {
                    List<SupplierOutput> newList = new ArrayList<>();
                    newList.add(newObj);
                    categorizedProducts.put(currentProduct.getSupplier() + orderNumber, newList);
                } else {
                    supplierOutputs.add(newObj);
                }
            }
        }
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
