package com.example.book.module.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.example.book.module.entity.Book;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface BookMapper {
    @Select("select * from book WHERE id=#{id} and is_deleted = 0")
    Book getById(@Param("id") BigInteger id);

    @Select("select * from book WHERE id=#{id}")
    Book extractById(@Param("id") BigInteger id);

    @Select("select * from book WHERE is_deleted = 0")
    List<Book> getAll();

    int update(@Param("book") Book book);

    int insert(@Param("book") Book book);

    int edit(@Param("book") Book book);

    @Update("update book set is_deleted=1, update_time=#{time} where id=#{id} limit 1")
    int delete(@Param("id") BigInteger id, @Param("time") Integer time);

    @Update("update book set is_deleted=1, update_time=#{time} where category_id=#{categoryId} limit 1")
    int deleteByCategoryId(@Param("categoryId") BigInteger categoryId, @Param("time") Integer time);

    // console分页查询
    List<Book> getByOffset(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("keyword") String keyword);

    // console total查询
    @Select("select count(*) from book where is_deleted = 0")
    int getTotal();

}