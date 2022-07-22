package string;

import java.util.ArrayList;
import java.util.List;

public class StringTest {

    static class P {
        public String name;
        public String url;

        public P(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

    public static void main(String[] args) {
        List<P> list = new ArrayList<>();

        list.add(new P("name", "http://xxx.com"));
        list.add(new P("name", "https://xxx.com"));

        System.out.println(list.get(0).name == list.get(1).name); // true
        String a = "a";
        String b = "a"; // true
        String c = "a".intern(); // true 默认就是intern()
        String d = a; // true
        String e = a.intern(); // true
        String f = new String("a"); // false
        String g = new String("a").intern(); // true

        System.out.println(b == a);// true
        System.out.println(c == a);// true
        System.out.println(d == a);// true
        System.out.println(e == a);// true
        System.out.println(f == a);// false
        System.out.println(g == a);// true

        // 测试final string
        String ab1 = "ab";
        String aa1 = "a";
        String bb1 = "b";
        String aabb1 = aa1 + bb1;
        System.out.println(ab1 == aabb1); // false

        String ab2 = "ab";
        final String aa2 = "a";
        final String bb2 = "b";
        final String aabb2 = aa2 + bb2;
        System.out.println(ab2 == aabb2); // true
        // javac StringTest.java
        // javap -v StringTest.class

        stringFinalTest();
    }

    /**
     * 测试String的不可变性
     * <p>
     * 以下你将可以看到两个hashcode是不一样的，也就是说他们的内存地址是不一样的
     */
    static void stringFinalTest() {
        String s = "hello";
        System.out.println(s.hashCode());
        s = "world";
        System.out.println(s.hashCode());
    }
}
