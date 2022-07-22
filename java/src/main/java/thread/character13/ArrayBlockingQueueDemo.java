package thread.character13;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * ArrayBlockingQueue 示例
 */
public class ArrayBlockingQueueDemo {

    private int queueSize = 10;
    private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(queueSize);


    public static void main(String[] args) {
        ArrayBlockingQueueDemo arrayBlockingQueueDemo = new ArrayBlockingQueueDemo();
        new Thread(arrayBlockingQueueDemo.new Producer()).start();
        new Thread(arrayBlockingQueueDemo.new Comsumer()).start();

    }

    class Comsumer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    queue.take();
                    System.out.println("从队列取走一个元素，队列剩余" + queue.size() + "个元素");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Producer implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    queue.put(1);
                    System.out.println("向队列取中插入一个元素，队列剩余空间：" + (queueSize - queue.size()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
