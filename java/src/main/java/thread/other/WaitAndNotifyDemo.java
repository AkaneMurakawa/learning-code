package thread.other;

/**
 * 两个线程交叉打印，一个打印数字，一个打印大写字母，结果为A1B2...Y25Z26
 */
public class WaitAndNotifyDemo {

    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ThreadA()).start();
        Thread.sleep(100);
        new Thread(new ThreadB()).start();
    }

    static class ThreadA implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                for (int i = 1; i <= 26; i++) {
                    try {
                        System.out.print((char) (i + 64));
                        // notifyAll()方法叫醒另一个正在等待的线程
                        lock.notifyAll();
                        // 使用wait()方法陷入等待并释放lock锁
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notifyAll();
            }
        }
    }

    static class ThreadB implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                for (int i = 1; i <= 26; i++) {
                    try {
                        System.out.print(i);
                        lock.notifyAll();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notifyAll();
            }
        }
    }
}
