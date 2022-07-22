package thread.character5;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 管道 示例
 * <p>
 * 管道是基于“管道流”的通信方式。
 * JDK提供了PipedWriter、 PipedReader、 PipedOutputStream、 PipedInputStream。
 * 其中，前面两个是基于字符的，后面两个是基于字节流的。
 * </p>
 */
public class PipeDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        PipedWriter pipedWriter = new PipedWriter();
        PipedReader pipedReader = new PipedReader();
        // 连接，通信
        pipedWriter.connect(pipedReader);
        new Thread(new ReaderThread(pipedReader)).start();
        Thread.sleep(1000);
        new Thread(new WriterThread(pipedWriter)).start();

    }

    static class ReaderThread implements Runnable {
        private PipedReader pipedReader;

        public ReaderThread(PipedReader pipedReader) {
            this.pipedReader = pipedReader;
        }

        @Override
        public void run() {
            System.out.println("this is reader");
            int receive = 0;
            try {
                while ((receive = pipedReader.read()) != -1) {
                    System.out.println((char) receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class WriterThread implements Runnable {
        private PipedWriter pipedWriter;

        public WriterThread(PipedWriter pipedWriter) {
            this.pipedWriter = pipedWriter;
        }

        @Override
        public void run() {
            System.out.println("this is writer");
            int receive = 0;
            try {
                pipedWriter.write("test");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    pipedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
