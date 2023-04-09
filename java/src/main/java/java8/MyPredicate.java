package main.java;

@FunctionalInterface // 检查是否为函数式接口
public interface MyPredicate<T> {

    void run();
}
