package mybatis.model.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Alias("user") // mybatis的别名，或使用<typeAliases>标签设置
@Data
public class User {

    private Integer id;

    private Integer userId;

    private String username;

    private String desc;
}
