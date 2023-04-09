package main.java;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(MyAnnotations.class) // 标记为可重复注解
public @interface MyAnnotation {

    String value();
}
