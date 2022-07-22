package thread.other;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport
 *
 * @param <V>
 */
public class Collection3Demo<V> {

    volatile List<V> list = new ArrayList();

    public void add(V v) {
        list.add(v);
    }

    public int size() {
        return list.size();
    }

    static Thread t1, t2 = null;

    public static void main(String[] args) {
        Collection3Demo<Integer> collection = new Collection3Demo<>();

        t1 = new Thread(() -> {
            System.out.println("t1开始");
            for (int i = 0; i < 10; i++) {
                collection.add(i + 1);
                System.out.println("add " + (i + 1));
                if (collection.size() == 5) {
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }

        });


        t2 = new Thread(() -> {
            System.out.println("t2开始");
            if (collection.size() != 5) {
                LockSupport.park();
            }
            System.out.println("t2结束");
            LockSupport.unpark(t1);

        });

        t1.start();
        t2.start();

    }
}
