package diff;

import diff.diff.DiffNode;
import lombok.Data;

@Data
public class DiffObject {

    @DiffNode(value = "名称", order = 1)
    private String name;

    @DiffNode("年龄")
    private Integer age;

    @DiffNode("简介")
    private String bio;

    public DiffObject(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    public DiffObject(String name, Integer age, String bio) {
        this.name = name;
        this.age = age;
        this.bio = bio;
    }
}
