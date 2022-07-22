package thread.character3;

import java.util.stream.IntStream;

/**
 * 线程优先级 示例
 * <p>
 * 1~10 低~高
 */
public class ThreadPriorityDemo {

    public static void main(String[] args) {
        Thread a = new Thread();
        // 默认优先级是 5
        // 从getPriority()源码里 if (newPriority > MAX_PRIORITY || newPriority < MIN_PRIORITY) {}
        // 我们可以得知优先级的范围
        System.out.println("我是默认线程优先级: " + a.getPriority());
        Thread b = new Thread();
        b.setPriority(10);
        System.out.println("我是设置过的线程优先级: " + b.getPriority());


        threadPriorityTest();
        threadGroupAndCurrentThread();
    }

    /**
     * 当线程和线程组的优先级不一致的时候将会怎样呢
     */
    private static void threadGroupAndCurrentThread() {
        ThreadGroup threadGroup = new ThreadGroup("线程组");
        threadGroup.setMaxPriority(4);
        Thread thread = new Thread(threadGroup, "thread");

        System.out.println("我是线程组的优先级: " + threadGroup.getMaxPriority());
        // 结果是4
        // 如果某个线程优先级大于线程所在线程组的最大优先级，那么该线程的优先级将会失效，取而代之的是线程组的最大优先级
        System.out.println("我是线程的优先级:" + thread.getPriority());
    }

    /**
     * Java中的优先级来说不是特别的可靠，Java程序中对线程所设置的优先级只是给操作系统一个建议，操作系统不一定会采纳。
     * 而真正的调用顺序，是由操作系统的线程调度算法决定的。
     */
    private static void threadPriorityTest() {
        IntStream.range(1, 10).forEach(i -> {

            Thread testThread = new Thread(new ThreadE());
            testThread.setPriority(i);
            testThread.start();

        });
    }

    static class ThreadE extends Thread {
        @Override
        public void run() {
            System.out.print("线程名: " + Thread.currentThread().getName());
            System.out.println("线程优先级: " + Thread.currentThread().getPriority());
        }
    }
}
