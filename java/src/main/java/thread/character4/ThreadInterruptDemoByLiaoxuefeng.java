package thread.character4;

/**
 * 线程中断 示例
 * 其他线程对其调用了interrupt()方法，通常情况下该线程应该立刻结束运行
 */
public class ThreadInterruptDemoByLiaoxuefeng {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new MyThread();
        t.start();
        Thread.sleep(1000);
        // main线程通过调用t.interrupt()从而通知t线程中断，
        // 而此时t线程正位于hello.join()的等待中，此方法会立刻结束等待并抛出InterruptedException
        t.interrupt(); // 中断t线程
        t.join(); // 等待t线程结束
        System.out.println("end");
    }
}

class MyThread extends Thread {
    public void run() {
        Thread hello = new HelloThread();
        hello.start(); // 启动hello线程
        try {
            hello.join(); // 等待hello线程结束
        } catch (InterruptedException e) {
            System.out.println("interrupted!");
        }
        // 如果去掉这一行代码，可以发现hello线程仍然会继续运行，且JVM不会退出
        hello.interrupt();
    }
}

class HelloThread extends Thread {
    public void run() {
        int n = 0;
        while (!isInterrupted()) {
            n++;
            System.out.println(n + " hello!");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

