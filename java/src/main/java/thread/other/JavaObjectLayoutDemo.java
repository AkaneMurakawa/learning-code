package thread.other;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * 对象头
 */
public class JavaObjectLayoutDemo {

    public static void main(String[] args) throws InterruptedException {
        final A a = new A();
        System.out.println("****hashCode");


        ClassLayout layout = ClassLayout.parseInstance(a);
        System.out.println("****Fresh object");
        // 当没遇到synchronized时，就是无锁状态
        // 第一个线程访问，markword 记录这个线程ID （偏向锁），如果后面还是这个线程，就不用加锁
        System.out.println(layout.toPrintable());// 01, 无锁可偏向，00000 0 01

        Thread t = new Thread(() -> {
            synchronized (a) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        t.start();


        TimeUnit.SECONDS.sleep(1);

        System.out.println("****Before the lock");
        // 如果后面是其它线程，线程争用：锁升级为轻量级锁， 自旋锁
        System.out.println(layout.toPrintable());// 00, 轻量级锁，11110 0 00

        // 默认自旋10次，10次后未获取到锁，升级为重量级锁（系统锁），进入等待队列
        synchronized (a) {
            System.out.println("****With the lock");
            System.out.println(layout.toPrintable());// 10, 重量级锁，01010 0 10
        }

        System.out.println("****After the lock");
        System.out.println(layout.toPrintable());// 10, 锁不会降级, 01010 0 10

        System.gc();

        System.out.println("****After System.gc()");
        System.out.println(layout.toPrintable());// 如果非11，可能未gc

    }
}


class A {

}
