package thread.character4;

/**
 * 线程状态转换 示例
 */
public class ThreadStateChangeDemo {

    public static void main(String[] args) throws InterruptedException {

//        newState();
//        blockedState();
//        timedWaitingState1();
//        timedWaitingState2();
    }

    /**
     * 超时等待
     * <p>
     * Thread.join() 调用join()方法不会释放锁，会一直等待当前线程执行完毕（转换为TERMINATED状态）
     *
     * @throws InterruptedException
     */
    private static void timedWaitingState2() throws InterruptedException {
        Thread a = new Thread(() -> {
            print();
        });
        Thread b = new Thread(() -> {
            print();
        });

        a.start();
        // 调用join()方法不会释放锁，会一直等待当前线程执行完毕（转换为TERMINATED状态）。
        a.join();
        b.start();
        // BLOCKED：阻塞状态，运行中的线程，因为某些操作被阻塞而挂起
        System.out.println("TERMINATED state: " + a.getState());
        System.out.println("TIMED_WAITING state: " + b.getState());
    }

    /**
     * 超时等待
     * <p>
     * Thread.sleep(1000L);
     *
     * @throws InterruptedException
     */
    private static void timedWaitingState1() throws InterruptedException {
        Thread a = new Thread(() -> {
            System.out.println("run()");
        });
        a.start();
        Thread.sleep(1000L);
        // 超时等待状态，运行中的线程，因为执行sleep()方法正在计时等待
        System.out.println("TIMED WAITING state: " + a.getState());
    }

    private static void blockedState() throws InterruptedException {
        Thread a = new Thread(() -> {
            print();
        });
        Thread b = new Thread(() -> {
            print();
        });

        a.start();
        // 暂停，为了线程 b 在等待锁
        Thread.sleep(10L);
        b.start();
        System.out.println("TIMED_WAITING state: " + a.getState());
        // BLOCKED：阻塞状态，运行中的线程，因为某些操作被阻塞而挂起
        // 等待锁
        System.out.println("BLOCKED state: " + b.getState());
    }

    /**
     * 新建状态
     *
     * @throws InterruptedException
     */
    private static void newState() throws InterruptedException {
        Thread a = new Thread(() -> {
            System.out.println("run()");
        });
        // 新建状态，还未调用start()
        System.out.println("NEW state: " + a.getState());
        a.start();
    }

    // 同步竞争锁
    private static synchronized void print() {
        System.out.println("同步竞争锁");
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



