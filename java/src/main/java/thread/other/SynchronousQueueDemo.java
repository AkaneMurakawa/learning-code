package thread.other;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue
 */
public class SynchronousQueueDemo { //容量为0
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> strs = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println("take:" + strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        strs.put("aaa"); // 阻塞等待消费者消费
        System.out.println("size:" + strs.size());
        strs.put("bbb"); // 无消费者，会一直阻塞
        System.out.println("size:" + strs.size());
    }
}
