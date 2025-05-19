package com.example.book.app.controller;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.example.book.app.domain.BookInfoVo;
import com.example.book.app.domain.BookListDetailsVo;
import com.example.book.app.domain.BookListVo;
import com.example.book.module.entity.Book;
import com.example.book.module.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    public BookController() {
    }

    @RequestMapping("/book/info")
    public BookInfoVo bookInfo(@RequestParam(name = "bookId") BigInteger bookId) {
        Book book = bookService.getBookInfoById(bookId);
        if (book == null) {
            return new BookInfoVo();
        }
        BookInfoVo bookInfoVo = new BookInfoVo();
        bookInfoVo.setImageList(new ArrayList<>(Arrays.asList(book.getImages().split("\\$"))));
        bookInfoVo.setBookTitle(book.getBookTitle());
        bookInfoVo.setBookRating(book.getBookRating());
        bookInfoVo.setBookReview(book.getBookReview());
        int timestamp = book.getCreateTime();
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = dateTime.format(formatter);
        bookInfoVo.setCreateTime(formattedTime);
        return bookInfoVo;
    }

    @RequestMapping({"/book/list"})
    public BookListVo bookAll(@RequestParam(name = "page", defaultValue = "1") int page,
                              @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword
    ) {
        int pageSize = 4;
        int offset = (page - 1) * pageSize;

        List<Book> bookList = this.bookService.getBookPageByOffset(offset, pageSize, keyword);
        if (bookList == null || bookList.isEmpty()) {
            return new BookListVo();
        }

        boolean isEnd = pageSize > bookList.size();
        List<BookListDetailsVo> bookListDetailsVoList = new ArrayList();
        Iterator var3 = bookList.iterator();

        while(var3.hasNext()) {
            Book book = (Book)var3.next();
            BookListDetailsVo bookListDetailsVo = new BookListDetailsVo();
            bookListDetailsVo.setBookId(book.getId());
            bookListDetailsVo.setImage(book.getImages().split("\\$")[0]);
            bookListDetailsVo.setBookTitle(book.getBookTitle());
            bookListDetailsVo.setBookRating(book.getBookRating());
            bookListDetailsVoList.add(bookListDetailsVo);
        }

        BookListVo bookListVo = new BookListVo();
        bookListVo.setList(bookListDetailsVoList);
        bookListVo.setIsEnd(isEnd);
        return bookListVo;
    }
}