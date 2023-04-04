package jvm;

public class CPU100Test {

    public static void main(String[] args) {
        CPU100Test.run();
    }

    static void run(){
        for (;;){
            String str = "123";
            str.replace("x", "*");
            System.out.println("a");
        }
    }
}
