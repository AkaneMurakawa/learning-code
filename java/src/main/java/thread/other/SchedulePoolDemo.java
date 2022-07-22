package thread.other;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * SchedulePool
 */
public class SchedulePoolDemo {

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        // initialDelay: 初始延迟时间
        // period: 周期
        // 2秒执行一次
        executor.scheduleAtFixedRate(() -> {
            System.out.println("***2秒执行一次****");
        }, 0, 2, TimeUnit.SECONDS);
    }
}
