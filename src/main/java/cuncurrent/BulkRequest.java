package cuncurrent;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.atomic.AtomicInteger;

public class BulkRequest implements Runnable {
    private String URL;
    private LeackyBucket leackyBucket;
    private RestTemplate restTemplate = new RestTemplate();
    private AtomicInteger count;


    public BulkRequest(String URL, LeackyBucket leackyBucket, AtomicInteger count) {
        this.URL = URL;
        this.leackyBucket = leackyBucket;
        this.count = count;

    }


    @Override
    public void run() {
        while (true) {
            leackyBucket.consume();
            restTemplate.exchange(URL, HttpMethod.GET, null, null);
            count.incrementAndGet();

        }
    }
}