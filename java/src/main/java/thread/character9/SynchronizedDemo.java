package thread.character9;

/**
 * Synchronized 示例
 */
public class SynchronizedDemo {

    /**
     * 关键字在实例方法上，锁为当前 this 实例
     */
    public synchronized void instanceLock() {
        // code
    }

    /**
     * 关键字在代码块上，锁为括号里面的对象
     */
    public void blockLock() {
        Object o = new Object();
        synchronized (o) {
            // code
        }
    }

    /*************************************************************************************/

    /**
     * 关键字在静态方法上，锁为当前Class对象
     * <p>
     * 对于static方法，是没有this实例的，因为static方法是针对类而不是实例。
     * 但是我们注意到任何一个类都有一个由JVM自动创建的Class实例，
     * 因此，对static方法添加synchronized，锁住的是该类的Class实例。
     * </p>
     */
    public static synchronized void classLock1() {
        // code
    }

    /**
     * 上面的 classLock1 相当于
     */
    public static void classLock2() {
        synchronized (SynchronizedDemo.class) {
            // code
        }
    }

}
