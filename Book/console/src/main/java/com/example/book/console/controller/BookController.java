package com.example.book.console.controller;

import com.example.book.console.domain.ConsoleInfoVo;
import com.example.book.console.domain.ConsoleListVo;
import com.example.book.console.domain.ConsoleListDetailsVo;
import com.example.book.console.domain.ConsoleStatusVo;
import com.example.book.module.entity.Book;
import com.example.book.module.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/book/add")
    public ConsoleStatusVo bookCreate(@RequestParam(name = "images") String images,
                                      @RequestParam(name = "bookTitle") String bookTitle,
                                      @RequestParam(name = "bookRating") Integer bookRating,
                                      @RequestParam(name = "bookReview") String bookReview) {
        int status = bookService.createBook(images, bookTitle.trim(), bookRating, bookReview);
        ConsoleStatusVo consoleInfoVo = new ConsoleStatusVo();
        consoleInfoVo.setStatus(1 == status ? "成功" : "失败");
        return consoleInfoVo;
    }

    @RequestMapping("/book/update")
    public ConsoleStatusVo bookUpdate(@RequestParam(name = "bookId") BigInteger bookId,
                                      @RequestParam(name = "images") String images,
                                      @RequestParam(name = "bookTitle") String bookTitle,
                                      @RequestParam(name = "bookRating") Integer bookRating,
                                      @RequestParam(name = "bookReview") String bookReview) {
        int status = bookService.updateBook(bookId, images, bookTitle.trim(), bookRating, bookReview);
        ConsoleStatusVo consoleInfoVo = new ConsoleStatusVo();
        consoleInfoVo.setStatus(1 == status ? "成功" : "失败");
        return consoleInfoVo;
    }

    @RequestMapping("/book/delete")
    public ConsoleStatusVo bookDelete(@RequestParam(name = "bookId") BigInteger bookId) {
        int status = bookService.deleteBook(bookId);
        ConsoleStatusVo consoleInfoVo = new ConsoleStatusVo();
        consoleInfoVo.setStatus(1 == status ? "成功" : "失败");
        return consoleInfoVo;
    }

    @RequestMapping("/book/info")
    public ConsoleInfoVo bookInfo(@RequestParam(name = "bookId") BigInteger bookId) {
        Book book = bookService.getBookInfoById(bookId);
        if (book == null) {
            return null;
        }
        ConsoleInfoVo consoleInfoVo = new ConsoleInfoVo();
        consoleInfoVo.setImageList(new ArrayList<>(Arrays.asList(book.getImages().split("\\$"))));
        consoleInfoVo.setBookTitle(book.getBookTitle());
        consoleInfoVo.setBookRating(book.getBookRating());
        consoleInfoVo.setBookReview(book.getBookReview());
        int timestamp = book.getCreateTime();
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = dateTime.format(formatter);
        consoleInfoVo.setCreateTime(formattedTime);
        return consoleInfoVo;
    }

    @RequestMapping({"/book/list"})
    public ConsoleListVo bookAll() {
        List<Book> consoleList = this.bookService.getAllBookInfo();
        List<ConsoleListDetailsVo> consoleListDetailsVoList = new ArrayList();
        Iterator var3 = consoleList.iterator();

        while(var3.hasNext()) {
            Book book = (Book)var3.next();
            ConsoleListDetailsVo consoleListDetailsVo = new ConsoleListDetailsVo();
            consoleListDetailsVo.setBookId(book.getId());
            consoleListDetailsVo.setImage(book.getImages().split("\\$")[0]);
            consoleListDetailsVo.setBookTitle(book.getBookTitle());
            consoleListDetailsVo.setBookRating(book.getBookRating());

            int create_timestamp = book.getCreateTime();
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(create_timestamp), ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = dateTime.format(formatter);
            consoleListDetailsVo.setCreateTime(formattedTime);

            int update_timestamp = book.getUpdateTime();
            dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(update_timestamp), ZoneId.systemDefault());
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            formattedTime = dateTime.format(formatter);
            consoleListDetailsVo.setUpdateTime(formattedTime);

            consoleListDetailsVoList.add(consoleListDetailsVo);
        }

        ConsoleListVo consoleListVo = new ConsoleListVo();
        consoleListVo.setList(consoleListDetailsVoList);
        return consoleListVo;
    }

}
