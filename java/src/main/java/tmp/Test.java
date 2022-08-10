package tmp;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("2");
        list.add("3");
        list.add("3");

        List<String> deduplication = getDuplication(list, v -> v);
        deduplication.forEach(System.out::println);
    }

    /**
     * 根据条件获取重复数据
     */
    public static <T, U extends Comparable<U>> List<U> getDuplication(List<T> list, Function<T, U> function) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().collect(Collectors.groupingBy(function, Collectors.counting()))
                .entrySet().stream().filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }
}
