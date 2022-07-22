package thread.other;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * PriorityBlockingQueue
 */
public class PriorityBlockingQueueDemo {
    static class User {
        int age;
        String name;

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 自定义比较
        PriorityBlockingQueue<User> queue = new PriorityBlockingQueue<>(10,
                (o1, o2) -> o1.age - o2.age
        );
        queue.put(new User(18, "a"));
        queue.put(new User(30, "b"));
        queue.put(new User(20, "c"));
        queue.put(new User(25, "d"));

        for (int i = 0; i < 4; i++) {
            System.out.println(queue.take().age);
        }
    }
}
