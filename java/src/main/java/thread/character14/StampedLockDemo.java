package thread.character14;

import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock 示例
 * <p>
 * StampedLock把读锁细分为乐观读和悲观读，能进一步提升并发效率。
 * 但这也是有代价的：一是代码更加复杂，二是StampedLock是不可重入锁，不能在一个线程中反复获取同一个锁。
 */
public class StampedLockDemo {

    private final StampedLock stampedLock = new StampedLock();

    private double x;
    private double y;

    /**
     * 写锁的使用
     *
     * @param deltaX
     * @param deltaY
     */
    public void move(double deltaX, double deltaY) {
        // 获取写锁
        long stamp = stampedLock.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            // 释放写锁
            stampedLock.unlockWrite(stamp);
        }
    }


    /**
     * 乐观读锁的使用
     *
     * @return
     */
    public double distanceFromOrigin() {
        // 获取一个乐观读锁
        long stamp = stampedLock.tryOptimisticRead();

        // 这两行代码不具有原子性
        // 这两行代码不具有原子性
        double currentX = x;
        double currentY = y;

        // 检查乐观读锁后是否有其他写锁发生
        if (!stampedLock.validate(stamp)) {
            // 获取一个悲观读锁
            stamp = stampedLock.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                // 释放悲观读锁
                stampedLock.unlockWrite(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    /**
     * 悲观读锁以及读锁升级写锁的使用
     */
    public void moveIfAtOrigin(double newX, double newY) {
        // 获取一个悲观读锁
        long stamp = stampedLock.readLock();

        try {
            while (x == 0.0 && y == 0.0) {
                // 读锁尝试转换为写锁：转换成功后相当于获取了写锁，转换失败相当于有写锁被占用
                long ws = stampedLock.tryConvertToWriteLock(stamp);

                // 如果转换成功
                if (ws != 0L) {
                    stamp = ws;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    // 释放读锁
                    stampedLock.unlockRead(stamp);
                    // 强制获取写锁
                    stamp = stampedLock.writeLock();
                }
            }

        } finally {
            stampedLock.unlock(stamp);
        }
    }

}
