package thread.other;

/**
 * 死锁示例
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            method1();
            method2();
        });

        Thread t2 = new Thread(() -> {
            method1();
            method2();
        });

        // 如果 method1() 和 method2() 都由两个或多个线程调⽤,则存在死锁的可能性
        t1.start();
        t2.start();

        // 解决方式：method1和method2使用相同的锁顺序，例如method1和method3不会死锁
        //Thread t3 = new Thread(() -> {
        //    method1();
        //    method3();
        //});
        //t3.start();
    }


    public static void method1() {
        synchronized (Integer.class) {
            System.out.println("lock on Integer class");
            synchronized (String.class) {
                System.out.println("lock on String class");
            }
        }
    }

    public static void method2() {
        synchronized (String.class) {
            System.out.println("lock on String class");
            synchronized (Integer.class) {
                System.out.println("lock on Integer class");

            }
        }
    }

    public static void method3() {
        synchronized (Integer.class) {
            System.out.println("lock on Integer class");
            synchronized (String.class) {
                System.out.println("lock on String class");
            }
        }
    }
}




