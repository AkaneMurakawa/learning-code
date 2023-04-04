package jvm;

/**
 * 对象的创建
 */
public class ObjectInitialTest {

    public static void main(String[] args) {
        System.out.println(T.count);

    }
}

class T {
    //---3
    //3
    //public static int count = 2;
    //public static T t = new T();

    //---1，此时得到的是count默认值0，++后变为1
    //2
    public static T t = new T();
    public static int count = 2;

    private T() {
        count++;
        System.out.println("---" + count);
    }
}
