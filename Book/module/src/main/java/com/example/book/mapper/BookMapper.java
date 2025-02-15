package com.example.book.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.example.book.entity.Book;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface BookMapper {
    @Select("select * from book WHERE id=#{id} and is_deleted = 0")
    Book getById(@Param("id") BigInteger id);

    @Select("select * from book WHERE is_deleted = 0")
    List<Book> getAll();

    int update(@Param("book") Book book);

    int insert(@Param("book") Book book);

    @Update("update book set is_deleted=1, update_time=#{time} where id=#{id} limit 1")
    int delete(@Param("id") BigInteger id, @Param("time") Integer time);
}