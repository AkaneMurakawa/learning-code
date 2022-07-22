package thread.character11;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock 示例
 * <p>
 * 只允许一个线程写入（其他线程既不能写入也不能读取）；
 * 没有写入时，多个线程允许同时读（提高性能）。
 * <p>
 * <p>
 * 1.把读写操作分别用读锁和写锁来加锁，在读取时，多个线程可以同时获得读锁，这样就大大提高了并发读的执行效率。
 * 2. 使用ReadWriteLock时，适用条件是同一个数据，有大量线程读取，但仅有少数线程修改。
 * <p>
 * 例如，一个论坛的帖子，回复可以看做写入操作，它是不频繁的，但是，浏览可以看做读取操作，是非常频繁的，这种情况就可以使用ReadWriteLock。
 */
public class ReentrantReadWriteLockDemo {

    private static final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static final Lock readLock = rwLock.readLock();
    private static final Lock writeLock = rwLock.writeLock();
    private static int value;

    /**
     * 写
     */
    public static void read(Lock lock) {
        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.println("read over!" + value);
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }

    }

    /**
     * 读
     */
    public static void write(Lock lock, int v) {
        try {
            lock.lock();
            Thread.sleep(1000);
            value = v;
            System.out.println("write over!");
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Runnable read = () -> read(readLock);
        Runnable write = () -> write(writeLock, new Random().nextInt(100));

        for (int i = 0; i < 10; i++) {
            new Thread(read).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(write).start();
        }
    }
}
