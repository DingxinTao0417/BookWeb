package com.example.book.module.mapper;

import com.example.book.module.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigInteger;
import java.util.List;
/**
* 分类 Mapper 接口
*/
@Mapper
public interface CategoryMapper{

    int insert(@Param("category") Category category);

    int update(@Param("category") Category category);

    @Update("update category set is_deleted=1 where id=#{id} limit 1")
    int delete(@Param("id") BigInteger id);

    @Select("SELECT category_name FROM category WHERE id=#{id}")
    String getCategoryNameById(@Param("id") BigInteger id);

    @Select("SELECT * FROM category")
    List<Category> getAllCategory();

    @Select("select count(*) from category where is_deleted = 0")
    int getTotal();

    List<Category> getByOffset(@Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Long> getCategoryIds(String keyword);

}
