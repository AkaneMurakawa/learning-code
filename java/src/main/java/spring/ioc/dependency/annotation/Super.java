package spring.ioc.dependency.annotation;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * 参考Component
 * Retention 策略
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Super {
}
