import cuncurrent.BulkRequest;
import cuncurrent.LeackyBucket;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import util.ThreadUtil;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by User on 28.07.2018.
 */
public class Main {
    private static String URL;
    private static Integer perSec;
    static {
        Properties properties = ReadProperty.readDataFromProperties();
        URL=properties.getProperty("url");
        perSec = Integer.valueOf(properties.getProperty("perSec"));
    }

    public static void main(String[] args) {

        AtomicInteger countOfRequest = new AtomicInteger();
        LeackyBucket leackyBucket = new LeackyBucket(perSec);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new BulkRequest(URL, leackyBucket,countOfRequest));
        int countOfTask = 0;


        while (true) {
            ThreadUtil.makeDelay();
            if (countOfRequest.get()<perSec){
                executorService.submit(new BulkRequest(URL, leackyBucket, countOfRequest));
                countOfTask++;
                System.out.println("Less then need ");
                System.out.println("count of task " + countOfTask);

            }
            System.out.println(countOfRequest.get());
        }
    }
}
