package thread.other;

import java.util.ArrayList;
import java.util.List;

/**
 * wait / notify
 *
 * @param <V>
 */
public class Collection1Demo<V> {

    volatile List<V> list = new ArrayList();

    public void add(V v) {
        list.add(v);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        Collection1Demo<Integer> collection = new Collection1Demo<>();
        final Object lock = new Object();

        Thread t1 = new Thread(() -> {
            System.out.println("t1开始");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    collection.add(i + 1);
                    System.out.println("add " + (i + 1));
                    if (collection.size() == 5) {
                        lock.notify(); // notify不会释放锁
                        try {
                            lock.wait(); // wait会释放锁
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }

        });


        Thread t2 = new Thread(() -> {
            System.out.println("t2开始");
            synchronized (lock) {
                if (collection.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2结束");
                lock.notify();
            }
        });

        t1.start();
        t2.start();

    }
}
