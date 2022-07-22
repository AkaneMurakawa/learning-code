package thread.other;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch
 *
 * @param <V>
 */
public class Collection2Demo<V> {

    volatile List<V> list = new ArrayList();

    public void add(V v) {
        list.add(v);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        Collection2Demo<Integer> collection = new Collection2Demo<>();

        CountDownLatch countDownLatch1 = new CountDownLatch(1);
        CountDownLatch countDownLatch2 = new CountDownLatch(1);

        Thread t1 = new Thread(() -> {
            System.out.println("t1开始");
            for (int i = 0; i < 10; i++) {
                collection.add(i + 1);
                System.out.println("add " + (i + 1));
                if (collection.size() == 5) {
                    countDownLatch2.countDown();
                    try {
                        countDownLatch1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });


        Thread t2 = new Thread(() -> {
            System.out.println("t2开始");
            if (collection.size() != 5) {
                try {
                    countDownLatch2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            countDownLatch1.countDown();

            System.out.println("t2结束");
        });

        t1.start();
        t2.start();

    }
}
