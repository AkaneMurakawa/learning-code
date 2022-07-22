package thread.character11;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁 ReentrantLock 示例
 * Re entrant Lock
 * <p>
 * <p>
 * 说明：Java的synchronized锁是可重入锁
 * JVM允许同一个线程重复获取同一个锁，这种能被同一个线程反复获取的锁，就叫做可重入锁
 */
public class ReentrantLockDemo {

    // 1. ReentrantLock是可重入锁，它和synchronized一样，一个线程可以多次获取同一个锁。
    // 2. 和synchronized不同的是，ReentrantLock可以尝试获取锁：
    // 3. 使用ReentrantLock比直接使用synchronized更安全，线程在tryLock()失败的时候不会导致死锁
    private final Lock lock = new ReentrantLock();

    private final Condition condition = lock.newCondition();
    private Queue<String> queue = new LinkedList<>();

    public static void main(String[] args) {

    }

    public void addTask(String s) {
        lock.lock();
        try {
            queue.add(s);
            // 相当于 synchronized 中的 notifyAll();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String getTask() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                // 相当于 synchronized 中的 wait();
                // 会释放当前锁，进入等待状态；
                condition.await();
            }
            return queue.remove();
        } finally {
            lock.unlock();
        }
    }
}
