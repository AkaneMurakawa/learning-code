package java8;

public interface MyInterface {

    default void run(){
        System.out.println("默认方法");
    }

    static void getTime(){
        System.out.println("静态方法");
    }
}
