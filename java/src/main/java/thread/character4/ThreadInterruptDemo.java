package thread.character4;

/**
 * 线程中断 示例
 * <p>
 * 在某些情况下，我们在线程启动后发现并不需要它继续执行下去时，需要中断线程。
 * 目前在Java里还没有安全直接的方法来停止线程，但是Java提供了线程中断机制来处理需要中断线程的情况。
 */
public class ThreadInterruptDemo {

    public static void main(String[] args) throws InterruptedException {
        test();
    }

    private static void test() throws InterruptedException {
        Thread a = new ThreadH();
        a.start();
        // 暂停1毫秒
        Thread.sleep(1);
        // 这里的中断线程并不会立即停止线程，而是设置线程的中断状态为true
        a.interrupt();
        boolean interrupted = a.isInterrupted();
        System.out.println("interrupted: " + interrupted);

        // 等待线程 a 结束
        a.join();
        System.out.println("调用join()方法，等待线程a结束，执行这段话");
    }
}

class ThreadH extends Thread {
    @Override
    public void run() {
        System.out.println("run()");
        while (!isInterrupted()) {
            System.out.println("无限剑制");
        }
    }
}
