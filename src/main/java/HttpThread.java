
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

import java.time.LocalTime;
import java.util.concurrent.Callable;


public class HttpThread implements Callable<Boolean> {

    private HttpGet httpGet;
    private CloseableHttpClient client;
    private CloseableHttpResponse response;
    private HttpClientContext httpClientContext;
    private int threadNumber;



    public HttpThread(HttpGet httpGet, CloseableHttpClient client, int threadNumber) {
        this.httpGet = httpGet;
        this.client = client;
        this.httpClientContext = HttpClientContext.create();
        this.threadNumber = threadNumber;
    }



    public Boolean call() throws Exception {
            try{
                response = client.execute(httpGet, httpClientContext);
                LocalTime time = LocalTime.now();
                System.out.println("Response code: "+ response.getStatusLine().getStatusCode()+"sending time: " + time);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }finally {
                response.close();
            }


        return true;
    }
}
