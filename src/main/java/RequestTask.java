
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;


public class RequestTask implements Callable<Boolean> {

    private HttpGet httpGet;
    private CloseableHttpClient client;
    private CloseableHttpResponse response;
    private HttpClientContext httpClientContext;
    private int threadNumber;

    public static AtomicLong startTime = new AtomicLong();
    public static AtomicLong endTime = new AtomicLong();



    public RequestTask(HttpGet httpGet, CloseableHttpClient client, int threadNumber) {
        this.httpGet = httpGet;
        this.client = client;
        this.httpClientContext = HttpClientContext.create();
        this.threadNumber = threadNumber;
    }
    private void setStartTime(long time){
            this.startTime.set(time);
    }
    private void setEndTime(long endTime){ this.endTime.set(endTime);

    }


    public Boolean call()  {
        System.out.println("request №"+ this.threadNumber +"send");
        try {
            long start = System.currentTimeMillis();
            if(startTime.get() == 0){
                setStartTime(start);
            }
                response = client.execute(httpGet, httpClientContext);
            long end = System.currentTimeMillis();
            setEndTime(end);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("exception occur during request execution request №" + this.threadNumber );
            return false;
        }

        try{
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("exception occur during releasing response resources request № " + this.threadNumber);
            return false;
        }finally {
            System.out.println("response №"+ this.threadNumber +"returned");
        }
        return true;

    }
}
