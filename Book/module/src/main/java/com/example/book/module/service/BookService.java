package com.example.book.module.service;

import com.example.book.module.entity.Book;
import com.example.book.module.mapper.BookMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class BookService {

    @Resource
    private BookMapper bookMapper;

    public Book getBookInfoById(BigInteger id) {
        if (id == null) {
            return null;
        }
        return bookMapper.getById(id);
    }

    public Book extractBookInfoById(BigInteger id) {
        if (id == null) {
            return null;
        }
        return bookMapper.extractById(id);
    }

    public List<Book> getAllBookInfo() {
        return bookMapper.getAll();
    }

    public List<Book> getBookPageByOffset(int offset, int limit) {
        if (offset < 0 || limit < 0) {
            return null;
        }
        return bookMapper.getByOffset(offset, limit, "");
    }

    public List<Book> getBookPageByOffset(int offset, int limit, String keyword) {
        if (offset < 0 || limit < 0 || keyword == null) {
            return null;
        }
        return bookMapper.getByOffset(offset, limit, keyword);
    }

    public int getBookTotal() {
        return bookMapper.getTotal();
    }

    public Result<Integer> createBook(String images, String bookTitle, Integer bookRating, String bookReview) {
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        Book book = new Book();
        book.setImages(images);
        book.setBookTitle(bookTitle);
        book.setBookRating(bookRating);
        book.setBookReview(bookReview);
        book.setCreateTime(timestamp);
        book.setUpdateTime(timestamp);
        book.setIsDeleted(0);

        try {
            int result = bookMapper.edit(book);
            if (result == 1) {
                return Result.success(result, "添加数据成功");
            } else {
                return Result.fail("添加数据失败");
            }
        } catch (Exception e) {
            return Result.fail("添加数据失败", e.getMessage());
        }
    }

    public Result<Integer> updateBook(BigInteger bookId, String images, String bookTitle, Integer bookRating, String bookReview) {
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        Book book = new Book();
        book.setId(bookId);
        book.setImages(images);
        book.setBookTitle(bookTitle);
        book.setBookRating(bookRating);
        book.setBookReview(bookReview);
        book.setUpdateTime(timestamp);

        try {
            int result =  bookMapper.edit(book);
            if (result == 1) {
                return Result.success(result, "更新数据成功");
            } else {
                return Result.fail("更新数据失败：bookId不存在");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.fail("更新数据失败", e.getMessage());
        }
    }

    public int deleteBook(BigInteger id) {
        if (id == null) {
            return 0;
        }
        return bookMapper.delete(id, (int) (System.currentTimeMillis() / 1000));
    }
}
