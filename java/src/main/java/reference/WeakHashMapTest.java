package reference;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * WeakHashMap
 * ThreadLocalMap类似于WeakHashMap，使⽤的 key 为 ThreadLocal 的弱引⽤，⽽ value 是强引⽤
 */
public class WeakHashMapTest {

    public static void main(String[] args) {
        WeakHashMap weakHashMap = new WeakHashMap();
        String a = new String("a");
        String b = new String("b");
        weakHashMap.put(a, "aaaa");
        weakHashMap.put(b, "bbbb");
        sout(weakHashMap);
        a = null;
        b = null;
        System.gc();
        System.out.println("after gc");
        sout(weakHashMap);
    }

    public static void sout(WeakHashMap weakHashMap) {
        Iterator iterator = weakHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            System.out.println("key:" + entry.getKey());
            System.out.println("value:" + entry.getValue());
        }

    }
}
