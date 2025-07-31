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

    @Select("SELECT category_name FROM category WHERE id=#{id} limit 99")
    String getCategoryNameById(@Param("id") BigInteger id);

    @Select("SELECT * FROM category limit 99")
    List<Category> getAllCategory();

    @Select("SELECT * FROM category WHERE is_deleted = 0 limit 99")
    List<Category> getValidCategory();

    @Select("select count(*) from category where is_deleted = 0 limit 99")
    int getTotal();

    List<Category> getByOffset(@Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Long> getCategoryIds(String keyword);

    @Select("SELECT * FROM category where is_deleted = 0 AND id=#{parentId}")
    List<Category> getParentCategory(@Param("parentId") BigInteger parentId);

    @Select("SELECT * FROM category where is_deleted = 0 AND parent_id=#{parentId} limit 99")
    List<Category> getByParentId(@Param("parentId") BigInteger parentId);

}
