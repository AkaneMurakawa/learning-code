package thread.character18;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo2 {

    static final int MAX_NUM = 1000;
    static int[] numbers = new int[10000];
    static Random r = new Random();

    static {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = r.nextInt(100);
        }
        System.out.println("stream api sum:" + Arrays.stream(numbers).sum()); //stream api
    }

    class SumTask extends RecursiveTask<Long> {
        int start, end;

        public SumTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            // 如果数量 < 最大数量 直接计算
            if (end - start <= MAX_NUM) {
                long sum = 0L;
                for (int i = start; i < end; i++) {
                    sum += numbers[i];
                }
                return sum;
            } else {
                int mid = start + (end - start) / 2;
                SumTask sumTask1 = new SumTask(start, mid);// 分叉
                SumTask sumTask2 = new SumTask(mid, end);// 分叉
                sumTask1.fork();
                sumTask2.fork();
                return sumTask1.join() + sumTask2.join(); // 汇总
            }
        }
    }


    public static void main(String[] args) {
        ForkJoinDemo2 demo = new ForkJoinDemo2();
        demo.test();
    }

    public void test() {
        // 使用ForkJoinPool
        System.out.println("CPU核数: " + Runtime.getRuntime().availableProcessors());
        long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        SumTask task = new SumTask(0, numbers.length);
        forkJoinPool.execute(task);
        System.out.println("result: " + task.join());
        long end = System.currentTimeMillis();
        System.out.println(String.format("耗时：%d millis", end - start));
    }
}
