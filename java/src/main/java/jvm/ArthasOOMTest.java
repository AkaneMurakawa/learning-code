package jvm;

class ArthasOOM{

    private byte[] b = new byte[1024 * 1024];

    public boolean say(String msg){
        System.out.println(msg);
        return true;
    }
}

/**
 * Arthas排除OOM问题测试
 * thread
 * thread 1
 */
public class ArthasOOMTest {

    public static void main(String[] args) {
        ArthasOOMTest.run();
    }

    static void run(){
        while (true){
            ArthasOOM arthasOOM = new ArthasOOM();
            arthasOOM.say("Hello");
        }
    }
}
