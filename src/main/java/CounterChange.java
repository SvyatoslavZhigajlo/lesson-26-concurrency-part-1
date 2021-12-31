import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class CounterChange implements Runnable {

    private AtomicInteger counter;
    private final CountDownLatch downLatch;


    public CounterChange (AtomicInteger counter, CountDownLatch downLatch) {
        this.counter = counter;
        this.downLatch = downLatch;
    }


    @Override
    public void run() {
        counter.incrementAndGet();
        downLatch.countDown();
    }
}
