package diff.diff;

import java.lang.annotation.*;

/**
 * Diff node
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DiffNode {

    String value() default "";

    int order() default 0;
}
