import java.io.IOException;

public class Main {


    public static void main(String[] args) throws InterruptedException, IOException {


        String host = PropertiesReader.getPropertiesByKey("host");
        String quantity = PropertiesReader.getPropertiesByKey("requestQuantity");
        int requestquantity = Integer.valueOf(quantity);

        SenderService senderService = new SenderService();
        senderService.sendRequests(host, requestquantity);

    }

}
