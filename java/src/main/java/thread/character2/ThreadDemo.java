package thread.character2;

/**
 * Thread 示例
 */
public class ThreadDemo {

    public static void main(String[] args) {
        // 以下两种是否有区别？
        // 无论是哪种方式，都是返回一个Thread对象
        // new ThreadA();调用的是无参构造，target是null
        // 在调用start的时候有一个native方法start0()，
        // 因此无法得知new ThreadA();的target是何时赋值的
        Thread a = new ThreadA();
        a.setName("Thread a");
        a.start();

        Thread b = new Thread(new ThreadA(), "Thread b");
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
        while (true) {
            System.out.println("Thread run" + this.getName());
            System.out.println();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
