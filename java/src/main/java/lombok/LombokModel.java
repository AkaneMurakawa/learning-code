package lombok;

import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;

/**
 * 相当于@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
 */
@Data
/**
 * 自动生成toString方法
 * callSuper = true表示也打印父类
 */
@ToString(callSuper = true)
/**
 * new User().setUsername("").setSalt("");
 */
@Accessors(chain = true)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LombokModel {

    /**
     * 自动生成set和get方法
     */
    @Getter @Setter
    private Long tid;

    @EqualsAndHashCode.Include
    private String username;

    @EqualsAndHashCode.Include
    private String salt;

    private String password;

    /**
     * 用于解决@Data @Builder的冲突
     */
    @Tolerate
    public LombokModel() {
    }
}
