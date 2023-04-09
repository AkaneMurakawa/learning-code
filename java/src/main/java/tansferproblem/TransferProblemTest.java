package tansferproblem;

import base.User;

/**
 * 值传递还是引用传递问题 示例
 * <p>
 * java中方法参数传递方式是 按值传递
 * 如果参数是基本类型，传递的是基本类型的字面量值的拷贝
 * 如果参数是引用类型，传递的是该参量所引用的对象在堆中地址值的拷贝
 * <p>
 * 结论：Java中的是值传递，首先要搞懂的是所谓的引用传递指的是传递引用地址。
 * 但是从上面的示例中，我们可以看到，传入的p1，重新new后，还是之前，说明其引用地址没有改变。
 */
public class TransferProblemTest {

    public static void main(String[] args) {
        User user = new User("a", "b");
        System.out.println("初始前：" + user);
        test(user);
        System.out.println("调用后：" + user);
    }

    static void test(User user) {
        user.setName("改变");
        user = new User("引用改变了？", "并没有");
    }

}
