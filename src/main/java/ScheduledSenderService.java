//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.protocol.HttpClientContext;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import java.io.IOException;
//import java.time.LocalTime;
//import java.util.Timer;
//import java.util.TimerTask;
//
//
//public class ScheduledSenderService {
//    private int interval;
//    private int requestQuantity;
//    private String host;
//    private Timer timer;
//
//
//    private PoolingHttpClientConnectionManager manager;
//    private CloseableHttpClient client ;
//
//    public ScheduledSenderService() {
//        String quantity = PropertiesReader.getPropertiesByKey("requestQuantity");
//        this.requestQuantity = Integer.valueOf(quantity);
//        manager = new PoolingHttpClientConnectionManager();
//        manager.setDefaultMaxPerRoute(requestQuantity);
//        manager.setMaxTotal(requestQuantity);
//
//        client= HttpClients
//                .custom().setConnectionManager(manager).build();
//        this.host = PropertiesReader.getPropertiesByKey("host");
//        this.interval = calcIntervalForExecution();
//        System.out.println("interval for execution = " + interval + " milliseconds");
//        timer = new Timer();
//    }
//
//
//
//
//    private int calcIntervalForExecution (){
//        String intervalStr = PropertiesReader.getPropertiesByKey("timeForExecution");
//        return  Integer.valueOf(intervalStr)*1000 / requestQuantity;
//
//    }
//
//    public void scheduleTasks(){
//        int counter = requestQuantity;
//        timer.schedule(new ScheduledRequestTask(client,new HttpGet(host)), 0,interval);
//
//    }
//
//    public class ScheduledRequestTask extends TimerTask {
//        private HttpClientContext httpClientContext;
//        private CloseableHttpClient client;
//
//        private HttpGet get;
//        public ScheduledRequestTask(CloseableHttpClient client, HttpGet get) {
//            this.client = client;
//            this.get = get;
//            httpClientContext = HttpClientContext.create();
//        }
//
//        public void run() {
//            LocalTime time = LocalTime.now();
//            System.out.println("execution started at"+ time);
//            try {
//                client.execute(this.get, httpClientContext);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//    }
//
//
//}
