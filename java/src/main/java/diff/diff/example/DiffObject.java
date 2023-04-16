package diff.diff.example;

import diff.diff.DiffNode;
import diff.diff.converters.ListConverter;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DiffObject {

    @DiffNode(value = "字符串", order = -1)
    private String s;

    @DiffNode("数字")
    private Integer i;

    @DiffNode("浮点型")
    private double d;

    @DiffNode("布尔")
    private boolean b;

    @DiffNode(value = "集合", converter = ListConverter.class)
    private List<String> l;
}
