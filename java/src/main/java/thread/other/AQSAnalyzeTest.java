package thread.other;

import java.util.concurrent.locks.ReentrantLock;

/**
 * AQS分析
 */
public class AQSAnalyzeTest {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();
        lock.unlock();
    }
}
