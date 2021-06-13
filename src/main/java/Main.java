import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static String inputDir;
    private static String outputDir;

    public static void main(String[] args) {
        try {

            // TODO compile code. make it a jar.
            // TODO sort supplier output list by timestamp descendant and price
            // TODO format xml output if possible
            // TODO add at the beginning of the xml file utc code etc.
            getIOWorkingDir(); // read from stdin input and output dirs
            processCurrentDir(); // read and process all files from inputDir

            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(inputDir);
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE); // watch event only on create new file
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

    // get the input and output working directory
    private static void getIOWorkingDir(){
        Scanner userInput = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Please provide the input directory path:");
        while(true) {
            inputDir = userInput.nextLine();
            if(Files.notExists(Paths.get(inputDir)) || !Files.isDirectory(Paths.get(inputDir))) {
                System.out.println("Please provide a valid input directory path:");
                continue;
            }
            break;
        }
        System.out.println("Please provide the output directory path:");
        while(true) {
            outputDir = userInput.nextLine();
            if(Files.notExists(Paths.get(outputDir))|| !Files.isDirectory(Paths.get(outputDir))){
                System.out.println("Please provide a valid output directory path:");
                continue;
            }
            if(inputDir.equals(outputDir)){
                System.out.println("Input and output directory path cannot be the same:\nPlease provide another output path:");
                continue;
            }
            break;
        }
    }

    private static void processCurrentDir() throws IOException {
        File folder = new File(inputDir);
        File[] listOfFiles = folder.listFiles();

        for (File currFile : listOfFiles) {
            if (currFile.isFile() && isValid(currFile.getName())) {
                doProcess(currFile.getName());
            } else {
                System.out.println("Bad input - " + currFile.getName() + " - " + getCurrentDate());
            }
        }
    }

    /***
     * validate the input file using regex
     * @param inputFile the file that will be checked
     * @return true or false
     */
    private static boolean isValid(String inputFile) {
        return inputFile.matches("orders([0-9][0-9]).xml");
    }

    private static void doProcess(String currentFile) throws IOException {
        System.out.println("Processing " + currentFile + "....");
        Map<String, List<ProductOutput>> categorizedProducts = OrderProcessor.process(new File(inputDir + '\\' + currentFile));
        for (Map.Entry<String, List<ProductOutput>> entry : categorizedProducts.entrySet()) {
            String supplier = entry.getKey();
            ProductsOutput prodsOut = new ProductsOutput(entry.getValue());
            File xmlOutput = new File(outputDir + '\\' + supplier + ".xml");
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.writeValue(xmlOutput, prodsOut);
        }
        System.out.println(currentFile + " successfully processed at " + getCurrentDate());
    }

    /***
     * get current date
     * @return current date
     */
    private static String getCurrentDate(){
        Calendar calendar = Calendar.getInstance(); // Returns instance with current date and time set
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(calendar.getTime());
    }

}