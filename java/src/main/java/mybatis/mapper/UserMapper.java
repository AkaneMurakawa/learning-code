package mybatis.mapper;

import mybatis.model.entity.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 代理Mapper接口
 */
@Mapper
@CacheNamespace // 开启二级缓存
public interface UserMapper {

    /**
     * 在带注解的映射器接口类中使用动态 SQL，可以使用 script 元素。比如:
     *   @Update({"<script>",
     *       "update Author",
     *       "  <set>",
     *       "    <if test='username != null'>username=#{username},</if>",
     *       "    <if test='password != null'>password=#{password},</if>",
     *       "    <if test='email != null'>email=#{email},</if>",
     *       "    <if test='bio != null'>bio=#{bio}</if>",
     *       "  </set>",
     *       "where id=#{id}",
     *       "</script>"})
     *     void updateAuthorValues(Author author);
     */
    /**
     * 映射器注解，复杂的再用XML
     *
     * @param userId
     * @return
     */
    @Select("select * from user where user_id = #{userId}")
    User selectOne(Integer userId);

    @Select("select * from user where user_id = #{userId}")
    User selectOneCache(Integer userId);

}
