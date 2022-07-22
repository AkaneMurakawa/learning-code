package thread.character2;

/**
 * Runnable 示例
 */
public class RunnableDemo {

    public static void main(String[] args) {
        /**
         * public
         * class Thread implements Runnable {
         * }
         */
        Thread b = new Thread(new ThreadB(), "Thread B");
        b.start();

        // Java8
        new Thread(() -> {
            System.out.println("Java8 Thread B");
        }).start();
    }
}

class ThreadB implements Runnable {

    @Override
    public void run() {
        System.out.println("Thread B");
    }
}
