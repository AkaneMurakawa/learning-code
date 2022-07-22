package thread.other;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile不支持原子性
 */
public class VolatileTest1 {

    volatile int count = 0;

    void sum() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        VolatileTest1 volatileTest1 = new VolatileTest1();
        List<Thread> threads = new ArrayList<>(10);

        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(volatileTest1::sum, "thread-" + i));
        }
        threads.forEach(o -> o.start());

        threads.forEach(o -> {
            try {
                o.join();
            } catch (InterruptedException e) {
            }
        });

        System.out.println("sum:" + volatileTest1.count); // != 100000
    }
}
