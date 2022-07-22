package jvm;

/**
 * static加载研究
 */
public class StaticLoadingTest {

    public static void main(String[] args) {
        System.out.println(T.count);

    }

}

class T {
    //---3
    //3
    //public static int count = 2;
    //public static T t = new T();

    //---1
    //2
    public static T t = new T();
    public static int count = 2;

    private T() {
        count++;
        System.out.println("---" + count);
    }
}
