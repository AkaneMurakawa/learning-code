package jvm;

/**
 * 对象的初始化顺序
 */
public class ClassLoaderOrder {

    // 准备
    public static final String STATIC_FINAL_FILED = "静态常量";

    // 准备阶段赋值为null，初始化阶段赋值为 静态变量
    public static String STATIC_FILED = "静态变量";

    // 创建对象时赋值
    public String filed = "变量";

    // 初始化阶段
    static {
        System.out.println(STATIC_FINAL_FILED);
        System.out.println(STATIC_FILED);
        System.out.println("静态初始块");
    }

    {
        System.out.println(filed);
        System.out.println("初始块");
    }

    public ClassLoaderOrder() {
        System.out.println("构造函数");
    }

    public static void main(String[] args) {
        new ClassLoaderOrder();
    }
}
