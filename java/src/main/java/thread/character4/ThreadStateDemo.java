package thread.character4;

/**
 * 线程状态 示例
 */
public class ThreadStateDemo {

    public static void main(String[] args) {
        Thread thread = new Thread(new ThreadG());
        System.out.println("thread state: " + thread.getState());
        thread.start();
        // 重复调用start()问题
        // thread.start();

        // 源码分析， 在这里有一个 threadStatus 状态控制，当调用start0()时 这个值就会改变
       /* if (threadStatus != 0)
            throw new IllegalThreadStateException();

        *//* Notify the group that this thread is about to be started
         * so that it can be added to the group's list of threads
         * and the group's unstarted count can be decremented. *//*
        group.add(this);

        boolean started = false;
        try {
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
                *//* do nothing. If start0 threw a Throwable then
                  it will be passed up the call stack *//*
            }
        }*/
        System.out.println("thread state: " + thread.getState());
    }
}


class ThreadG extends Thread {
    @Override
    public void run() {
        System.out.print("线程名: " + Thread.currentThread().getName());
    }
}
