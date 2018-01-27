import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Main {


    public static void main(String[] args) throws InterruptedException, IOException, TimeoutException, ExecutionException {

        String host = PropertiesReader.getPropertiesByKey("host");
        System.out.println("host: " + host);
        String quantity = PropertiesReader.getPropertiesByKey("requestQuantity");
        int requestquantity = Integer.valueOf(quantity);
        System.out.println("requestAmountInPropFile: " + requestquantity);
        Integer interval = calcIntervalForExecution();
        System.out.println("interval betwen sending requests: "+interval);

        SenderService senderService = new SenderService();
        senderService.sendRequestsScheduledByTime(host,requestquantity,interval);

    }

    private static int calcIntervalForExecution (){
        String quantity = PropertiesReader.getPropertiesByKey("requestQuantity");
        int requestquantity = Integer.valueOf(quantity);
        String intervalStr = PropertiesReader.getPropertiesByKey("timeForExecution");
        return  Integer.valueOf(intervalStr)*1000 / requestquantity;

    }
}
