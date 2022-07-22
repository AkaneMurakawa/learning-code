package generics;

/**
 * 泛型 示例
 */
public class GenericsTest {

    public static void main(String[] args) {
        Pair<Integer> p1 = new Pair<>(123, 456);
        System.out.println("add:" + add(p1));

        Pair<Number> p2 = new Pair<>(1.23, 4.56);
        Pair<Number> p3 = new Pair<>(123, 456);
        setSame(p2, 10, 20);
        setSame(p3, 10, 20);

        System.out.println("p2:" + p2);
        System.out.println("p3:" + p3);

        Pair<Number> p4 = create(123, 456);
        System.out.println("p4:" + p4);
    }

    /**
     * 下届通配符 示例
     * 允许写不能读
     *
     * @param p
     * @param first
     * @param last
     */
    static void setSame(Pair<? super Number> p, Integer first, Integer last) {
        System.out.println(p.getFirst());
        p.setFirst(first);
        p.setLast(last);
    }

    /**
     * 上届通配符 示例
     * 允许读不能写
     *
     * @param p
     * @return
     */
    static int add(Pair<? extends Number> p) {
        Number first = p.getFirst();
        Number last = p.getLast();
        //p.setFirst(new Integer(first.intValue() + 100));
        //p.setLast(new Integer(last.intValue() + 100));
        return first.intValue() + last.intValue();
    }

    // 对静态方法使用<T>:
    // static Pair<T> create(T first, T last) {
    // 编写泛型类时，要特别注意，泛型类型<T>不能用于静态方法
    // 但实际上，这个<T>和Pair<T>类型的<T>已经没有任何关系了
    static <T> Pair<T> create(T first, T last) {
        return new Pair<>(first, last);
    }
    // 对于静态方法，我们可以单独改写为“泛型”方法，只需要使用另一个类型即可。
    // 对于上面的create()静态方法，我们应该把它改为另一种泛型类型，例如，<K>：

    /**
     * 静态方法 使用泛型 示例
     *
     * @param first
     * @param last
     * @param <K>
     * @return
     */
    static <K> Pair<K> create2(K first, K last) {
        return new Pair<>(first, last);
    }
}
