package diff.diff;

import diff.diff.converters.Converter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import java.util.Objects;

/**
 * diff row
 */
@Data
@Builder
public class DiffRow {

    private String fieldName;

    private Object left;

    private Object right;

    private Class type;

    private Class<? extends Converter<?>> converter;

    private int order;

    private Tag tag;

    public Tag getTag() {
        // left is null or blank, right not blank
        if ((Objects.isNull(left) || StringUtils.isBlank(left.toString()))
                && Objects.nonNull(right) && StringUtils.isNotBlank(right.toString())) {
            return Tag.INSERT;
        }
        // left not blank, right is null or blank
        if (Objects.nonNull(left) && StringUtils.isNotBlank(left.toString())
                && (Objects.isNull(right) || StringUtils.isBlank(right.toString()))) {
            return Tag.DELETE;
        }
        if (Objects.isNull(left) || StringUtils.isBlank(left.toString())
                && (Objects.isNull(right) || StringUtils.isBlank(right.toString()))) {
            return Tag.EQUAL;
        }
        if (left.equals(right)){
            return Tag.EQUAL;
        }
        return Tag.CHANGE;
    }

    @AllArgsConstructor
    public enum Tag {
        INSERT,
        DELETE,
        CHANGE,
        EQUAL,
        ;

        public boolean is(Tag tag){
            return this.name().equals(tag.name());
        }
    }
}