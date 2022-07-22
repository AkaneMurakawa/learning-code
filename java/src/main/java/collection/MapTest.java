package collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Map
 */
public class MapTest {

    public static void main(String[] args) {
        //Map
        //HashMap
        //ConcurrentHashMap
        //
        //System.out.println("size: " + tableSizeFor(16));
        iterator();
    }

    /**
     * 遍历示例
     */
    static void iterator() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("语文", 100);
        map.put("数学", 120);
        map.put("英语", 97);
        map.put("物理", 96);
        map.put("化学", 99);

        Set<String> keys = map.keySet();
        for (String key : keys) {
            System.out.println(map.get(key));
        }

        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= (1 << 30)) ? (1 << 30) : n + 1;
    }
}
