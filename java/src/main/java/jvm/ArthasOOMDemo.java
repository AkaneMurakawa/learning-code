package jvm;

class ArthasOOM{

    private byte[] b = new byte[1024 * 1024];

    public boolean say(String msg){
        System.out.println(msg);
        return true;
    }
}

/**
 *
 * thread
 * thread 1
 */
public class ArthasOOMDemo {

    public static void main(String[] args) {
        ArthasOOMDemo.run();
    }

    static void run(){
        while (true){
            ArthasOOM arthasOOM = new ArthasOOM();
            arthasOOM.say("Hello");
        }
    }
}
