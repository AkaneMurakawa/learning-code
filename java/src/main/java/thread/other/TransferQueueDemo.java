package thread.other;

import java.util.concurrent.LinkedTransferQueue;

/**
 * TransferQueue
 */
public class TransferQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();

        new Thread(() -> {
            try {
                System.out.println("take：" + strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        strs.transfer("aaa");
        System.out.println("transfer");
        strs.put("bbbb"); // aync，不进行阻塞
        new Thread(() -> {
            try {
                System.out.println("take：" + strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        strs.transfer("dddd"); // 进行阻塞
        try {
            // 前面dddd一行已经阻塞，因此不会调用这一行，但是前面注释掉同样会进行阻塞
            System.out.println("take：" + strs.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
