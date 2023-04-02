package jvm;

class Arthas{
    public boolean say(String msg){
        System.out.println(msg);
        return true;
    }
}

/**
 * 查看函数返回
 * watch jvm.ArthasDemo.say primeFactors returnObj
 *
 * 反编译
 * jad jvm.ArthasDemo
 */
public class ArthasDemo {

    public static void main(String[] args) throws InterruptedException {
        Arthas arthas = new Arthas();
        while (true){
            arthas.say("Hello");
            Thread.sleep(1000);
        }
    }
}
