import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            // read all files from dir

            WatchService watchService = FileSystems.getDefault().newWatchService();
            // read from stdin input and output dirs
            // compile code. make it a jar.
            // do - while
            // sort supplier output list by timestamp descendant
            String patchtoinputdir = "c://desktop/in";
            String patchtooutputdir = "c://desktop/out";
            Path path = Paths.get(System.getProperty("user.dir"));

            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    String currentFile = event.context().toString();
                    if (isValid(currentFile)) {
                        Map<String, List<ProductOutput>> categorizedProducts = OrderProcessor.process(new File(currentFile));
                        System.out.println("Processing " + event.context() + "....");
                        for (Map.Entry<String, List<ProductOutput>> entry : categorizedProducts.entrySet()) {
                            String supplier = entry.getKey();
                            ProductsOutput prodsOut = new ProductsOutput(entry.getValue());

                            //List<ProductOutput> products = entry.getValue();
                            File xmlOutput = new File(supplier + ".xml");
                            XmlMapper xmlMapper = new XmlMapper();
                            xmlMapper.writeValue(xmlOutput, prodsOut);
                        }
                        System.out.println(event.context() + " successfully processed!");
                    }
                }
                key.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // inputFile validation - ordersXX.xml
    private static boolean isValid(String inputFile) {
        return inputFile.matches("orders([0-9][0-9]).xml");
    }



}


//                    final Path path = Files.createTempFile(currentProduct.getSupplier() + "23", ".xml");
//                    if (Files.exists(path)) {
//                        ProductOutput newObj = new ProductOutput(currentProduct.getDescription(), currentProduct.getGtin(), currentProduct.getPrice(), currentOrder.getID());
//                        supplierProducts.add(newObj);
//                    } else {
//                        File newSupplierFile = new File(currentProduct.getSupplier() + "23.xml");
//                    }