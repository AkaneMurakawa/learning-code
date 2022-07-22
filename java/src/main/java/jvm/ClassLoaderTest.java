package jvm;

/**
 * 双亲委派
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        System.out.println("ClassLodarDemo's ClassLoader is " + jvm.ClassLoaderTest.class.getClassLoader());
        System.out.println("The Parent of ClassLodarDemo's ClassLoader is " + jvm.ClassLoaderTest.class.getClassLoader().getParent());
        System.out.println("The GrandParent of ClassLodarDemo's ClassLoader is " + jvm.ClassLoaderTest.class.getClassLoader().getParent().getParent());

    }
}
