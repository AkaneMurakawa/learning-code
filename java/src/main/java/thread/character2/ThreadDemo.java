package thread.character2;

/**
 * Thread 示例
 */
public class ThreadDemo {

    public static void main(String[] args) {
        // 以下两种是否有区别？
        Thread a = new ThreadA();
        a.start();

        Thread b = new Thread(new ThreadA());
        b.start();

        System.out.println(Thread.currentThread().getThreadGroup().activeCount());
        System.out.println("thread A state: " + a.getState());
        System.out.println("thread A name: " + a.getName());
        System.out.println("thread B state: " + b.getState());
        System.out.println("thread B name: " + b.getName());

        System.out.println("thread main name: " + Thread.currentThread().getName());
    }
}

class ThreadA extends Thread {
    @Override
    public void run() {

        System.out.println("Thread A");
    }
}
