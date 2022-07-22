package thread.character5;

/**
 * join() 示例
 * <p>
 * 底层调用的还是wait()
 */
public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ThreadA());
        thread.start();
        // 如果无，会先打印----等待线程 thread执行完毕，再执行当前线程 这句话
        thread.join();
        System.out.println("等待线程 thread执行完毕，再执行当前线程 这句话");
    }

    static class ThreadA implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("我是子线程，我先睡一秒");
                Thread.sleep(1000L);
                System.out.println("我是子线程，我睡完了一秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
