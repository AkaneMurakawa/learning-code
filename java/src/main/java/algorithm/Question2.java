package algorithm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Given a “flatten” dictionary object, whose keys are dot-separated.
 * For example, { ‘A’: 1, ‘B.A’: 2, ‘B.B’: 3, ‘CC.D.E’: 4, ‘CC.D.F’: 5}.
 * Implement a function to transform it to a “nested” dictionary object.
 * In the above case, the nested version is like:
 * {
 * ‘A’: 1,
 * ‘B’: {
 * ‘A’: 2,
 * ‘B’: 3
 * },
 * ‘CC’: {
 * ‘D’: {
 * ‘E’: 4,
 * ‘F’: 5
 * }
 * }
 * }
 * <p>
 * It’s guaranteed that no keys in dictionary are prefixes of other keys.
 */
public class Question2 {

    private static String SPLIT_STR_DOT = ".";


    public static void main(String[] args) {
        Map<String, Integer> flatten = new HashMap<>();
        flatten.put("A", 1);
        flatten.put("B.A", 2);
        flatten.put("B.B", 3);
        flatten.put("CC.D.E", 4);
        flatten.put("CC.D.F", 5);

        Map<String, Object> newMap = new TreeMap<>(); // sort Map
        proccess(flatten, newMap);
        sout(newMap);
    }


    /**
     * @param flatten 原 map
     * @param newMap  转换后的Map
     */
    public static void proccess(Map<String, Integer> flatten, Map<String, Object> newMap) {
        flatten.forEach((k, v) -> {
            transform(newMap, k, v);
        });
    }

    /**
     * @param map
     * @param k
     * @param v
     */
    public static void transform(Map<String, Object> map, String k, Integer v) {
        if (!k.contains(SPLIT_STR_DOT)) {
            map.put(k, v);
        } else {
            int i = k.indexOf(SPLIT_STR_DOT);
            String beforeKey = k.substring(0, i);
            String afterKey = k.substring(i + 1);

            Map<String, Object> tempMap = new HashMap<>();
            if (!map.containsKey(beforeKey)) {
                map.put(beforeKey, tempMap);
            } else {
                tempMap = (HashMap<String, Object>) map.get(beforeKey);
            }
            transform(tempMap, afterKey, v);
        }
    }

    public static void sout(Map<String, Object> flatten) {
        Iterator<Map.Entry<String, Object>> iterator = flatten.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            System.out.println(next.getKey() + ":" + next.getValue());
        }
    }
}
