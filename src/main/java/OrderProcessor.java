import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.util.*;

/***
 * Class specialized on xml deserialization
 */

public final class OrderProcessor {
    /***
     * Takes all the products from an orderXX.xml file and categorized them by supplier
     * @param inputFile File that will be deserialized
     * @return A map with categorized products by supplier
     * @throws IOException
     */
    public static Map<String, List<ProductOutput>> process(File inputFile) throws IOException {
        Map<String, List<ProductOutput>> categorizedProducts = new HashMap<>();
        XmlMapper xmlMapper = new XmlMapper();
        String xml = inputStreamToString(new FileInputStream(inputFile));
        Orders orders = xmlMapper.readValue(xml, Orders.class);
        String orderNumber = inputFile.getName().replace("orders", "").replace(".xml", "");
        for (Order currentOrder : orders.getOrders()) {
            for (Product currentProduct : currentOrder.getProducts()) {
                ProductOutput newObj = new ProductOutput(currentOrder.getCreated(), currentProduct.getDescription(),
                        currentProduct.getGtin(), currentProduct.getPrice(), currentOrder.getID());
                List<ProductOutput> productOutputs =
                        categorizedProducts.get(currentProduct.getSupplier() + orderNumber);
                if (productOutputs == null) {
                    List<ProductOutput> newList = new ArrayList<>();
                    newList.add(newObj);
                    categorizedProducts.put(currentProduct.getSupplier() + orderNumber, newList);
                } else {
                    productOutputs.add(newObj);
                    Collections.sort(productOutputs);
                }
            }
        }
        return categorizedProducts;
    }

    /***
     * Takes all the input from a file
     * @param is Input stream from file
     * @return A string with the input file content
     * @throws IOException
     */
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
