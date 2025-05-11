package com.example.book.module.service;

import com.example.book.module.entity.Book;
import com.example.book.module.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class BookService {

    @Resource
    private BookMapper bookMapper;

    public Book getBookInfoById(int id) {
        return bookMapper.selectById(id);
    }

    public Book extractBookInfoById(int id) {return bookMapper.extractById(id);}

    public List<Book> getAllBookInfo() { return bookMapper.getAll(); }

    public List<Book> getBookPageByOffset(int offset, int limit) {
        return bookMapper.getByOffset(offset, limit, "");
    }

    public List<Book> getBookPageByOffset(int offset, int limit, String keyword) {
        return bookMapper.getByOffset(offset, limit, keyword);
    }

    public int getBookTotal() {
        return bookMapper.getTotal();
    }

    public int createBook(String images, String bookTitle, Integer bookRating, String bookReview) {
        int timestamp = (int) (System.currentTimeMillis() / 1000);

        Book book = new Book();
        book.setImages(images);
        book.setBookTitle(bookTitle);
        book.setBookRating(bookRating);
        book.setBookReview(bookReview);
        book.setCreateTime(timestamp);
        book.setUpdateTime(timestamp);
        book.setIsDeleted(0);

        return bookMapper.insert(book);
    }

    public int updateBook(int bookId, String images, String bookTitle, Integer bookRating, String bookReview) {
        int timestamp = (int) (System.currentTimeMillis() / 1000);

        Book book = new Book();
        book.setId(bookId);
        book.setImages(images);
        book.setBookTitle(bookTitle);
        book.setBookRating(bookRating);
        book.setBookReview(bookReview);
        book.setUpdateTime(timestamp);

        return bookMapper.updateById(book);
    }

    public int deleteBook(int id) {
        return bookMapper.deleteById(id);
    }
}
