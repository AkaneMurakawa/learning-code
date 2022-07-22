package thread.character2;

import java.util.concurrent.*;

/**
 * Callable 示例
 *
 * <p>
 * 通常来说，我们使用Runnable和Thread来创建一个新的线程。
 * 但是它们有一个弊端，就是run方法是没有返回值的。
 * 而有时候我们希望开启一个线程去执行一个任务，并且这个任务执行完成后有一个返回值。
 * </p>
 *
 * <p>
 * JDK提供了Callable接口与Future类为我们解决这个问题，这也是所谓的“异步”模型。
 * Callable一般是配合线程池工具ExecutorService来使用的
 * </p>
 *
 * <p>
 * 在很多高并发的环境下，有可能Callable和FutureTask会创建多次。FutureTask能够在高并发环境下确保任务只执行一次
 * </p>
 */
public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 使用Executors创建线程池，但是阿里巴巴Java开发手册不建议使用
        // 原因是为了规避资源耗尽风险，也让开发者更了解线程池规则
        ExecutorService executorService = Executors.newCachedThreadPool();

        ThreadC c = new ThreadC();
        // 带返回结果的

        Future result = executorService.submit(c);
        // 注意调用get方法会阻塞当前线程，直到得到结果
        // 用途：阻塞，一直到线程结束，常用于线程池
        result.get();

        futureMethodDemo(result);
        futureTask();
    }

    /**
     * public class FutureTask<V> implements RunnableFuture<V> {}
     * public interface RunnableFuture<V> extends Runnable, Future<V> {}
     * <p>
     * Future只是一个接口，而它里面的cancel，get，isDone等方法要自己实现起来都是非常复杂的。所以JDK提供了一个FutureTask类来供我们使用。
     */
    private static void futureTask() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        FutureTask<Integer> futureTask = new FutureTask<>(new ThreadC());
        // 调用该submit方法是没有返回值的，Runnable无返回值
        // 上面的 Future Demo，调用的是 public <T> Future<T> submit(Callable<T> task) {}
        // 而这里是 Future<?> submit(Runnable task);
        Future<?> submit = executor.submit(futureTask);
        System.out.println(futureTask.get());
    }

    /**
     * Future 一些方法 示例
     */
    private static void futureMethodDemo(Future future) throws ExecutionException, InterruptedException {
        // 试图取消，并不一定能取消成功。因为任务可能已完成、已取消、或者一些其它因素不能取消，存在取消失败的可能
        // mayInterruptIfRunning 表示是否采用中断的方式取消线程执行。
        future.cancel(true);

        // 是否取消
        future.isCancelled();
        // 是否完成
        future.isDone();

        // 获取call()调用结果 注意调用get方法会阻塞当前线程，直到得到结果
        future.get();
    }
}

/**
 * public interface Callable<V> {}
 * 可指定call()返回类型
 */
class ThreadC implements Callable {

    @Override
    public Object call() throws Exception {
        System.out.println("Thead C");
        return Thread.currentThread().getName();
    }
}
