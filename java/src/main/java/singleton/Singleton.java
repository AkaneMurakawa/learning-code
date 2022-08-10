package singleton;

/**
 * 双重检查(同步代码块)
 */
public class Singleton {

    // 私有化构造方法
    private Singleton() {
    }

    // volatile保证内存可见性，防止指令重排
    private static volatile Singleton instance;

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
