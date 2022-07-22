package collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * List
 */
public class ListTest {

    public static void main(String[] args) {
        //List
        //ArrayList
        //LinkedList

        nullTest();
        linkedListTest();
    }

    static void linkedListTest() {
        LinkedList<Integer> list = new LinkedList<>();
        // 尾插入
        list.add(1);
        list.add(2);
        // 采用listIterator迭代器
        Iterator<Integer> iterator = list.listIterator(0);
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println(next);
        }
    }

    /**
     * 看源码是支持null的，删除的时候会判断是否为null
     */
    static void nullTest() {
        List<String> list = new ArrayList<>();
        list.add(null);
        list.forEach(System.out::println);

        System.out.println("remove null:" + list.remove(null));
        list.forEach(System.out::println);
    }
}
