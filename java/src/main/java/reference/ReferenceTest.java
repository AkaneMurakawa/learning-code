package reference;

import base.User;

/**
 * 反射 示例
 */
public class ReferenceTest {

    public static void main(String[] args) throws ClassNotFoundException {
        // 方法一：直接通过一个class的静态变量class获取
        Class clazz1 = User.class;
        // 方法二：如果我们有一个实例变量，可以通过该实例变量提供的getClass()方法获取：
        User user = new User("1", "2");
        Class<? extends User> clazz2 = user.getClass();
        // 方法三：如果知道一个class的完整类名，可以通过静态方法Class.forName()获取：
        Class clazz3 = Class.forName("base.User");

        // 因为Class实例在JVM中是唯一的，所以，上述方法获取的Class实例是同一个实例
        System.out.println(clazz1 == clazz2);
        System.out.println(clazz3 == clazz2);

    }
}
