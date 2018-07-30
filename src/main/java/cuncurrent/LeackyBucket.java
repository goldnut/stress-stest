package cuncurrent;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by User on 28.07.2018.
 */
public class LeackyBucket {
    private long minTimeNano;
    private long sched = System.nanoTime();

    public LeackyBucket(double perSec) {
        if (perSec <= 0.0) {
            throw new RuntimeException("Invalid rate " + perSec);
        }
        this.minTimeNano = (long) (1_000_000_000.0 / perSec);
    }

    public void consume() {
        long curr = System.nanoTime();
        long timeLeft;
        synchronized (this) {
            timeLeft = sched - curr + minTimeNano;
            sched += minTimeNano;
        }
        if (timeLeft <= minTimeNano) {
            return;
        }
        try {
            TimeUnit.NANOSECONDS.sleep(timeLeft);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
