package diff.diff;

import lombok.Builder;
import lombok.Data;

/**
 * diff row
 */
@Data
@Builder
public class DiffRow {

    private String fieldName;

    private Object lhs;

    private Object rhs;

    private int order;
}