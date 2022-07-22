package reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 强引用、弱引用、软引用
 */
public class WeakAndSoftReferenceTest {

    public static void main(String[] args) {
        // String a = "a";
        String a = new String("a");//强引用
        WeakReference<String> weakReference = new WeakReference<>(a);// 弱引用
        System.out.println(weakReference.get()); // a
        System.gc();
        a = null; // 去掉强引用
        System.out.println(weakReference.get()); // null

        String b = new String("b");//强引用
        SoftReference<String> stringSoftReference = new SoftReference<>(b);//软引用
        System.out.println(stringSoftReference.get()); // b
        b = null; // 去掉强引用
        System.gc();
        System.out.println(stringSoftReference.get()); // b
    }
}
