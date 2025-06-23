package ${package.Mapper};

import ${package.Entity}.${entity};
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;
import java.util.List;
/**
* ${table.comment} Mapper 接口
*/
@Mapper
public interface ${entity}Mapper{

    int insert(@Param("${entity?uncap_first}") ${entity} ${entity?uncap_first});

    int update(@Param("${entity?uncap_first}") ${entity} ${entity?uncap_first});

    @Update("update ${entity?uncap_first} set is_deleted=1 where id=<#noparse>#{id}</#noparse> limit 1")
    int delete(@Param("id") BigInteger id);

    @Select("SELECT ${entity?uncap_first}_name FROM ${entity?uncap_first} WHERE id=<#noparse>#{id}</#noparse>")
    String get${entity}NameById(@Param("id") BigInteger id);

    @Select("SELECT * FROM ${entity?uncap_first}")
    List<${entity}> getAll${entity}();

    @Select("select count(*) from ${entity?uncap_first} where is_deleted = 0")
    int getTotal();

    List<${entity}> getByOffset(@Param("offset") Integer offset, @Param("limit") Integer limit);

}
