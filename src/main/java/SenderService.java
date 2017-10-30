
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.xml.datatype.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SenderService {
    private PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
    private ExecutorService executor;
    private CloseableHttpClient client;


    public void sendRequests(String host, int requestQuantity) throws InterruptedException {
        manager.setDefaultMaxPerRoute(requestQuantity);
        manager.setMaxTotal(requestQuantity);
        executor = Executors.newFixedThreadPool(requestQuantity);
        List<Callable<Boolean>> httpThreads =  genereteThreadsList(requestQuantity, host);
        System.out.println("start sending request");
        executor.invokeAll(httpThreads);
        System.out.println("all request has been send");
        Long stop = System.currentTimeMillis();
        executor.shutdown();
        manager.shutdown();

    }

    private List<Callable<Boolean>> genereteThreadsList(int threadsCount, String hostName){
        List<Callable<Boolean>> threadsList = new LinkedList<Callable<Boolean>>();

        for (int i = 0; i <= threadsCount; i++){
            HttpGet get = new HttpGet(hostName);
            client = HttpClients
                    .custom().setConnectionManager(manager).build();
            threadsList.add(new HttpThread(get,client,i));
        }
        return threadsList;
    }





















}
