package thread.character3;

/**
 * 线程组 示例
 */
public class ThreadGroupDemo {

    public static void main(String[] args) {
        System.out.println("父线程(当前执行new Thread的线程)线程组名称: " + Thread.currentThread().getThreadGroup().getName());

        // 创建线程的时候可以指定线程组，如未指定，默认使用父线程的线程组
        // public Thread(ThreadGroup group, String name) {}
        Thread testThread = new Thread(() -> {
            System.out.println("testThread所在线程组名称: " + Thread.currentThread().getThreadGroup().getName());
        });
        testThread.start();

        threadGroupMethod();
        globalException();
    }

    /**
     * 线程组的方法 示例
     */
    private static void threadGroupMethod() {
        // 获取线程组名称
        Thread.currentThread().getThreadGroup().getName();

        // 复制一个线程数组到一个线程组
        Thread[] threads = new Thread[Thread.currentThread().getThreadGroup().activeCount()];
        ThreadGroup threadGroup = new ThreadGroup("threadGroup");
        threadGroup.enumerate(threads);
    }

    /**
     * 线程组统一异常处理 示例
     */
    private static void globalException() {
        ThreadGroup threadGroup = new ThreadGroup("thread group exception") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("线程组中的成员报异常会执行以下方法");
                super.uncaughtException(t, e);
            }
        };

        Thread thread = new Thread(threadGroup, () -> {
            throw new RuntimeException();
        });
        thread.start();
    }

    /**
     *
     * ThreadGroup 源码
     *
     * class ThreadGroup implements Thread.UncaughtExceptionHandler {
     *     // 父ThreadGroup
     *     private final ThreadGroup parent;
     *     String name;
     *     int maxPriority;
     *     boolean destroyed;
     *     boolean daemon;
     *     // 是否可以中断
     *     boolean vmAllowSuspension;
     *
     *     int nUnstartedThreads = 0;
     *     int nthreads;
     *     // ThreadGroup中的线程
     *     Thread threads[];
     *
     *     int ngroups;
     *     // 线程组数组
     *     ThreadGroup groups[];
     */
}
