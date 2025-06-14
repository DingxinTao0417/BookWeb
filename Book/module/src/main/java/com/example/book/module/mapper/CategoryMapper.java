package com.example.book.module.mapper;

import com.example.book.module.entity.Book;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import com.example.book.module.entity.Category;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("SELECT category_name FROM category WHERE id=#{id}")
    String getCategoryNameById(@Param("id") BigInteger id);

    @Select("SELECT * FROM category")
    List<Category> getAllCategory();

    @Select("select count(*) from categpru where is_deleted = 0")
    int getTotal();

    List<Category> getByOffset(@Param("offset") Integer offset, @Param("limit") Integer limit);

}
