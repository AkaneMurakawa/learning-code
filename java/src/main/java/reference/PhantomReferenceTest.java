package reference;

import base.User;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * 虚引用
 */
public class PhantomReferenceTest {

    private static final ReferenceQueue<User> QUEUE = new ReferenceQueue<>();
    private static final List<byte[]> LIST = new ArrayList<>();

    public static void main(String[] args) {

        PhantomReference<User> phantomReference = new PhantomReference<>(new User("name", "url"), QUEUE);

        new Thread(() -> {
            while (true) {
                LIST.add(new byte[1024 * 1024 * 100]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(phantomReference.get());
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Reference<? extends User> poll = QUEUE.poll();
                if (poll != null) {
                    System.out.println("poll" + poll);
                    System.out.println("通知，虚引用被回收了");
                }
            }
        }).start();

    }
}
