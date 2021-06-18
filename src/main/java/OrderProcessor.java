import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/***
 * Class specialized on xml serialization/deserialization
 */
public class OrderProcessor {

    private static String inputDir;
    private static String outputDir;

    public void Run() {

        try {
            getIOWorkingDir();
            processCurrentDir();

            // Instantiate a new watchService in order to look over the input directory
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(inputDir);
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey key;
            System.out.println("\nWaiting for files...");
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    String currentFile = event.context().toString();
                    if (isValid(currentFile)) {
                        doProcess(currentFile);
                    }
                }
                key.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * Get the input and output working directory from stdin
     */
    private void getIOWorkingDir() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Please provide the input directory path:");
        while (true) {
            inputDir = userInput.nextLine();
            if (Files.notExists(Paths.get(inputDir)) || !Files.isDirectory(Paths.get(inputDir))) {
                System.out.println("Please provide a valid input directory path:");
                continue;
            }
            break;
        }
        System.out.println("Please provide the output directory path:");
        while (true) {
            outputDir = userInput.nextLine();
            if (Files.notExists(Paths.get(outputDir)) || !Files.isDirectory(Paths.get(outputDir))) {
                System.out.println("Please provide a valid output directory path:");
                continue;
            }
            if (inputDir.equals(outputDir)) {
                System.out.println("Input and output directory path cannot be the same:\nPlease provide another " +
                        "output path:");
                continue;
            }
            break;
        }
    }

    /***
     * Take all the files in the working directory and process them
     * @throws IOException
     */
    private void processCurrentDir() throws IOException {
        List<File> files = Files.list(Paths.get(inputDir))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".xml"))
                .map(Path::toFile)
                .collect(Collectors.toList());

        for (File currFile : files) {
            if (isValid(currFile.getName())) {
                doProcess(currFile.getName());
            } else {
                System.out.println("Bad input - " + currFile.getName() + " - " + getCurrentDate());
            }
        }
    }

    /***
     * Checks if the input file is valid using regex
     * @param inputFile the file that will be checked
     * @return True or false
     */
    private boolean isValid(String inputFile) {
        return inputFile.matches("orders([0-9][0-9]).xml");
    }

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
     * Process the current validated file and writes in xml file
     * @param currentFile the file that is being processed
     * @throws IOException
     */
    private void doProcess(String currentFile) throws IOException {
        System.out.println("Processing " + currentFile + "....");
        Map<String, List<ProductOutput>> categorizedProducts =
                OrderProcessor.process(new File(inputDir + '\\' + currentFile));
        for (Map.Entry<String, List<ProductOutput>> entry : categorizedProducts.entrySet()) {
            String supplier = entry.getKey();
            ProductsOutput prodsOut = new ProductsOutput(entry.getValue());
            Collections.reverse(prodsOut.getProducts()); // Sort the products descendant by date and price
            File xmlOutput = new File(outputDir + '\\' + supplier + ".xml");
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true); // Add the XML header
            xmlMapper.writeValue(xmlOutput, prodsOut);
        }
        System.out.println(currentFile + " successfully processed at " + getCurrentDate());
    }

    /***
     * Takes all the input from a file
     * @param is Input stream from file
     * @return A string with the input file content
     * @throws IOException
     */
    private static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }


    /***
     * Get the current system date
     * @return Current system date
     */
    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(calendar.getTime());
    }

}
