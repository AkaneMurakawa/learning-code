package diff.diff;

import diff.diff.converters.AutoConverter;
import diff.diff.converters.Converter;

import java.lang.annotation.*;

/**
 * Diff node
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DiffNode {

    /**
     * 字段名称
     */
    String value() default "";

    /**
     * 转换
     */
    Class<? extends Converter<?>> converter() default AutoConverter.class;

    int order() default 0;
}
