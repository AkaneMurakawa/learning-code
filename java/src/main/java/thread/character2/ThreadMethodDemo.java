package thread.character2;

/**
 * Thread一些方法 示例
 */
public class ThreadMethodDemo {

    public static void main(String[] args) throws InterruptedException {
        // 返回对当前正在执行的线程对象的引用；native方法
        Thread currentThread =
                Thread.currentThread();

        // 启动，加了synchronized
        currentThread.start();
        // 放弃，让出一段时间CPU
        currentThread.yield();
        // 睡眠，使当前线程睡眠一段时间，会释放CPU资源，但不释放锁
        Thread.sleep(1);
        // 等待加入，当前线程等待另一个线程执行完毕之后再继续执行
        currentThread.join();

        // 设置优先级
        currentThread.setPriority(2);
        // 设置为守护线程
        currentThread.setDaemon(true);
        // 是否中断
        currentThread.isInterrupted();
        // 尝试中断
        currentThread.interrupt();

    }
}
