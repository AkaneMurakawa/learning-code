package main.java;

import main.java.model.User;
import sun.text.resources.el.CollationData_el;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Java8 {

    public static void main(String args[]){
        // Lambda函数式编程
        new Thread(
                () -> {
                    System.out.println("Lambda");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("end");
                }
        ).start();
        // 从匿名内部类开始
        Runnable t1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Run");
            }
        };
        Runnable t2 = () -> System.out.println("Lambda");

        // 使用自定义的函数式接口
        handler(() -> System.out.println("Lambda"));

        // -------------------------------------------------------------------------------------------

        // 方法引用
        Consumer<String> con = System.out::println;

        Comparator<Integer> com1 = (x, y) -> Integer.compare(x, y);
        Comparator<Integer> com2 = Integer::compare;

        BiPredicate<String, String> bp1 = (x, y) -> x.equals(y);
        BiPredicate<String, String> bp2 = String::equals;

        // 构造器引用
        Supplier<User> sup1 = () -> new User();
        Supplier<User> sup2 = User::new;

        // 数组引用
        // 以前
        Function<Integer, String[]> fun1 = (x) -> new String[x];
        // 现在
        Function<Integer, String[]> fun2 = String[]::new;


        // -------------------------------------------------------------------------------------------

        List<User> list = new ArrayList<>();
        String[] strs = new String[3];
        list.add(new User("a", 15, "http://xxx.com/xxx"));
        list.add(new User("b", 16, "http://xxx.com/xxx"));
        list.add(new User("c", 17, "http://xxx.com/xxx"));
        list.add(new User("d", 18, "http://xxx.com/xxx"));

        // 创建Stream的四种方式
        // 1.通过集合提供的stream()
        Stream<User> stream1 = list.stream();
        // 2.通过Arrays.stream()
        Stream<String> stream2 = Arrays.stream(strs);
        // 3.通过Stream.of
        Stream<String> stream3 = Stream.of("a", "b");
        // 4. Stream.iterate创建 无限流
        Stream<Integer> stream4 = Stream.iterate(0, x -> (x + 2));
        // 5.通过Stream.generate创建 无限流
        Stream<Double> stream5 = Stream.generate(() -> Math.random());


        // 中间操作
        // filter
        // limit
        // skip
        // distinct
        // sorted
        // map
        // flatMap
        // collect
        List<String> nameList1 = list.stream()
                .filter(Objects::nonNull) // Predicate 过滤
                .limit(3) // 截断流
                .skip(2) // 跳过元素
                .distinct() // 去除重复
                .sorted() // 自然排序
                .sorted((x, y) -> {
                    return x.getAge().compareTo(y.getAge());
                }) // 自定义排序
                .map(User::getName) // Function 将元素转换为其他形式
                .collect(Collectors.toList()); // 终止操作

        List<List<User>> list2 = new ArrayList<>();
        Stream<User> userStream = list2.stream()
                // <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
                .flatMap(userList -> userList.stream()); // 将元素转换成另一个流，然后将所有流连接成一个流


        // 内部迭代，迭代器由Stream API实现，foreach
        list.forEach(System.out::println);
        // 终止操作
        boolean b1 = list.stream()
                .allMatch(user -> user.getAge() > 18);// 所有匹配
        boolean b2 = list.stream()
                .anyMatch(user -> user.getAge() > 18); // 至少匹配一个
        boolean b3 = list.stream()
                .noneMatch(user -> user.getAge() > 18); // 检查是否没有匹配一个
        Optional<User> first = list.stream()
                .findFirst(); // 返回第一个
        Optional<User> any = list.stream()
                .filter(user -> user.getAge() > 18)
                .findAny(); // 返回任何一个
        long count = list.stream()
                .filter(user -> user.getAge() > 18)
                .count(); // 返回元素的总个数
        Optional<User> max = list.stream()
                .max((x, y) -> {
                    return x.getAge().compareTo(y.getAge());
                }); // 返回最大的
        Optional<User> min = list.stream()
                .min((x, y) -> {
                    return x.getAge().compareTo(y.getAge());
                }); // 返回最小的、

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = integers.stream()
                .reduce(0, (x, y) -> x + y); // reduce规约，将元素反复结合起来，得到一个值

        integers.stream()
                .collect(Collectors.toList()); // collect 收集
        integers.stream()
                .collect(Collectors.toSet()); // 收集
        integers.stream()
                .collect(Collectors.toCollection(HashSet::new)); // 收集
        integers.stream()
                .collect(Collectors.counting()); // 收集 counting
        integers.stream()
                .collect(Collectors.averagingInt(Integer::intValue)); // 收集 averagingInt
        integers.stream()
                .collect(Collectors.maxBy(Integer::compare)); // 收集 maxBy
        list.stream()
                .collect(Collectors.groupingBy(User::getName)); // 收集 groupingBy
        list.stream()
                .collect(Collectors.partitioningBy(user -> user.getAge() > 18)); // 收集 partitioningBy
        list.stream()
                .map(User::getName)
                .collect(Collectors.joining(",")); // 收集 joining


        // -----------------------------------------------------------------------------------------------------

        list.parallelStream();
        LongStream.rangeClosed(0, 10000000000L)
                .parallel() // 并行
                .reduce(0, Long::sum);

        // -----------------------------------------------------------------------------------------------------
        User user = new User("b", 16, "http://xxx.com/xxx");
        Optional<User> userOptional = Optional.of(user);// of 构造方法
        User user1 = userOptional.get(); // 获取内容

        Optional<Object> empty = Optional.empty(); // 创建一个空的Optional示例
        Optional<User> optional = Optional.ofNullable(user); // 有则创建一个Optional示例，否则创建一个空的Optional示例

        userOptional.isPresent(); // 是否存在
        User c = userOptional.orElse(new User("c", 16, "http://xxx.com/xxx")); // 有值返回值，否则返回传入的值
        userOptional.orElseGet(() -> new User("d", 16, "http://xxx.com/xxx"));


        // -----------------------------------------------------------------------------------------------------
        LocalDateTime ldt = LocalDateTime.now(); // 获取当前时间
        LocalDateTime dateTime = LocalDateTime.of(2020, 6, 27, 12, 25, 0);
        dateTime.plusMonths(1); // 加一个月

        Instant start = Instant.now(); // 时间戳
        Instant end = Instant.now();
        long seconds = Duration.between(start, end).toMillis(); // 时间间隔，毫秒
        start.atOffset(ZoneOffset.ofHours(8));


        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.of(2020, 6, 27);
        Period.between(now, date).getDays(); // 日期间隔

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");


    }

    /**
     * 自定义的函数式接口
     * @param predicate
     */
    public static void handler(MyPredicate predicate){
        predicate.run();
    }
}
