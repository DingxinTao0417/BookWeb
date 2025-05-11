package com.example.book.module.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.example.book.module.entity.Book;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
    @Select("select * from book WHERE id=#{id} and is_deleted = 0")
    Book getById(@Param("id") int id);

    @Select("select * from book WHERE id=#{id}")
    Book extractById(@Param("id") int id);

    @Select("select * from book WHERE is_deleted = 0")
    List<Book> getAll();

    //int update(@Param("book") Book book);

    //int insert(@Param("book") Book book);

    //@Update("update book set is_deleted=1, update_time=#{time} where id=#{id} limit 1")
    //int delete(@Param("id") BigInteger id, @Param("time") Integer time);

    // console分页查询
    //List<Book> getByOffset(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("keyword") String keyword);
    default List<Book> getByOffset(Integer offset, Integer limit, String keyword) {
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        if(keyword != null && !keyword.isEmpty()) {
            wrapper.like("book_title", keyword);
        }
        wrapper.eq("is_deleted", 0).orderByDesc("id");
        return this.selectList(wrapper.last("LIMIT " + offset + "," + limit));
    }

    // console total查询
    @Select("select count(*) from book where is_deleted = 0")
    int getTotal();

}