package milestone3;

import java.util.concurrent.atomic.AtomicInteger;

public class Generator {
    private final static AtomicInteger counter = new AtomicInteger();

    public static synchronized int getId() {
        return counter.getAndIncrement();
    }
}