package thread.other;

import org.openjdk.jol.info.ClassLayout;

/**
 * markword，锁升级测试
 */
public class JavaObjectLayoutDemo {

    public static void main(String[] args) throws InterruptedException {

        final Object obj = new Object();
        ClassLayout layout = ClassLayout.parseInstance(obj);
        // 当对象为无锁(new)时，偏向锁标记为0，锁标记为01
        System.out.println(String.format("****无锁，初始化new\n%s", layout.toPrintable())); // 无锁状态，00000 0 01

        System.out.println("****hashCode: " + Integer.toHexString(obj.hashCode())); // 1a 8a 8f 7c
        System.out.println(String.format("****无锁，调用hashCode\n%s", layout.toPrintable())); // 无锁状态，00000 0 01

        // 未模拟，当对象为偏向锁时，存储的是当前线程指针，偏向锁标记为1，锁标记为01

        // 模拟长时间占用锁，锁升级
        Runnable runnable = () -> {
            synchronized (obj) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                }
            }
        };
        new Thread(runnable).start();
        // 让上面的线程先指向
        Thread.sleep(1000);

        // 当对象为轻量级锁时，存储的是线程栈中锁记录的指针，锁标记为00
        System.out.println(String.format("****锁竞争，轻量级锁\n%s", layout.toPrintable()));// 轻量级锁，111010 00

        // 当对象为重量级锁时，存储的是互斥量的指针，锁标记为10
        synchronized (obj) {
            System.out.println(String.format("****重量级锁\n%s", layout.toPrintable()));// 重量级锁，011110 10
        }

        // GC标记
        System.gc();
        System.out.println(String.format("****GC标记\n%s", layout.toPrintable()));// 如果非11，可能未gc
    }
}
