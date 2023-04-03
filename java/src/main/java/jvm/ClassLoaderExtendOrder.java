package jvm;

/**
 * 对象的初始化过程
 */
class Parent {

    public static String STATIC_FILED = "父类静态变量";

    public String filed = "父类变量";

    static {
        System.out.println(STATIC_FILED);
        System.out.println("父类静态初始块");
    }

    {
        System.out.println(filed);
        System.out.println("父类初始块");
    }

    public Parent() {
        System.out.println("父类构造函数");
    }
}

class Son extends Parent{
    public static String STATIC_FILED = "子类静态变量";

    public String filed = "子类变量";

    static {
        System.out.println(STATIC_FILED);
        System.out.println("子类静态初始块");
    }

    {
        System.out.println(filed);
        System.out.println("子类初始块");
    }

    public Son() {
        super();
        System.out.println("子类构造函数");
    }
}

public class ClassLoaderExtendOrder {

    public static void main(String[] args) {
        new Son();
    }
}
