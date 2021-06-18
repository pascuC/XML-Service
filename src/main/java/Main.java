public class Main {

    public static void main(String[] args) {
        try {
            OrderProcessor orderProcessor = new OrderProcessor();
            orderProcessor.Run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}