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

    private void validateParameters(String images, String bookTitle,
                                    Integer bookRating, String bookReview) {
        if (images == null || images.trim().isEmpty()) {
            throw new RuntimeException("图片不能为空");
        }
        if (bookTitle == null || bookTitle.trim().isEmpty()) {
            throw new RuntimeException("书名不能为空");
        }
        if (bookRating == null || bookRating < 0 || bookRating > 5) {
            throw new RuntimeException("评分必须在0-5之间且不能为空");
        }
        if (bookReview == null || bookReview.trim().isEmpty()) {
            throw new RuntimeException("书评不能为空");
        }
    }

    public BigInteger editBook(BigInteger bookId, String images, String bookTitle, Integer bookRating, String bookReview) {
        validateParameters(images, bookTitle, bookRating, bookReview);

        int timestamp = (int) (System.currentTimeMillis() / 1000);
        Book book = new Book();
        book.setImages(images);
        book.setBookTitle(bookTitle);
        book.setBookRating(bookRating);
        book.setBookReview(bookReview);
        book.setUpdateTime(timestamp);

        if (bookId == null) {
            book.setCreateTime(book.getUpdateTime());
            book.setIsDeleted(0);
            bookMapper.edit(book);
            return book.getId();
        } else {
            if (bookMapper.getById(bookId) == null) {
                throw new RuntimeException("书籍不存在");
            }
            book.setId(bookId);
            bookMapper.edit(book);
            return bookId;
        }
    }

    public int deleteBook(BigInteger id) {
        if (id == null) {
            return 0;
        }
        return bookMapper.delete(id, (int) (System.currentTimeMillis() / 1000));
    }
}
