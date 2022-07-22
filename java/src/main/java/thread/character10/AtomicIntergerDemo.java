package thread.character10;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Atomic 示例
 */
public class AtomicIntergerDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        int i = atomicInteger.incrementAndGet();
        i = atomicInteger.incrementAndGet();
        System.out.println(i);
    }
}
