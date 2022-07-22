package thread.character18;


import java.util.concurrent.*;

/**
 * ForkJoin 示例
 * <p>
 * 上面我们说ForkJoinPool负责管理线程和任务，ForkJoinTask实现fork和join操作，
 * 所以要使用Fork/Join框架就离不开这两个类了，
 * 只是在实际开发中我们常用ForkJoinTask的子类RecursiveTask 和RecursiveAction来替代ForkJoinTask。
 */
public class ForkJoinDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ForkJoinDemo demo = new ForkJoinDemo();
        demo.test();
    }

    public void test() throws InterruptedException, ExecutionException {
        System.out.println("CPU核数: " + Runtime.getRuntime().availableProcessors());
        long start = System.currentTimeMillis();

        // 使用ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Fibonacci fibonacci = new Fibonacci(40);
        ForkJoinTask<Integer> future = forkJoinPool.submit(fibonacci);
        System.out.println("result: " + future.get());
        long end = System.currentTimeMillis();
        System.out.println(String.format("耗时：%d millis", end - start));
    }


    /**
     * extends RecursiveTask<Integer>
     */
    class Fibonacci extends RecursiveTask<Integer> {

        int n;

        public Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            // 这里先假设 n >= 0
            if (n <= 1) {
                return n;
            } else {
                // f(n-1)
                Fibonacci f1 = new Fibonacci(n - 1);
                // f(n-2)
                Fibonacci f2 = new Fibonacci(n - 2);
                f1.fork(); // 分叉
                f2.fork(); // 分叉

                // f(n) = f(n-1) + f(n-2)
                return f1.join() + f2.join(); // 汇总
            }
        }
    }
}
