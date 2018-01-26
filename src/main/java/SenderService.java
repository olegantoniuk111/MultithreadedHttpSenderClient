
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;


public class SenderService {
    private PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
    private ExecutorService executor;
    private CloseableHttpClient client = HttpClients
            .custom().setConnectionManager(manager).build();
    public static AtomicLong startTime = new AtomicLong();
    public static AtomicLong endTime = new AtomicLong();

    private int getThreadsQuantity() {
        String threadsQuantityForExecutorService = null;
        try{
            threadsQuantityForExecutorService = PropertiesReader.getPropertiesByKey("threadsQuantity");
        }catch (NoSuchPropertieInPropertiesFileException e){
            System.out.println("Propertie with name " +e.getMessage()+ " not exist. Check application.properties file");
            System.exit(0);
        }

        return Integer.valueOf(threadsQuantityForExecutorService);
    }

    public void sendRequests(String host, int requestQuantity)  {

        manager.setDefaultMaxPerRoute(requestQuantity);
        manager.setMaxTotal(20);
        executor = Executors.newFixedThreadPool(getThreadsQuantity());
        List<Callable<Boolean>> httpThreads =  generateRequestTasks(requestQuantity, host);
        try {
            executor.invokeAll(httpThreads);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Exception occur during execution requesrtSendingTasks");
        }
        executor.shutdown();
        manager.shutdown();

    }

    private List<Callable<Boolean>> generateRequestTasks(int threadsCount, String hostName){
        List<Callable<Boolean>> tasks = new LinkedList<Callable<Boolean>>();

        for (int i = 0; i <= threadsCount; i++){
            HttpGet get = new HttpGet(hostName);
            tasks.add(new RequestTask(get, client, i));
        }
        return tasks;
    }





















}
