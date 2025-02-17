package com.example.book.console.controller;

import com.example.book.console.domain.ConsoleStatusVo;
import com.example.book.module.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/book/add")
    public ConsoleStatusVo bookCreate(@RequestParam(name = "images") String images,
                                      @RequestParam(name = "bookTitle") String bookTitle,
                                      @RequestParam(name = "bookRating") Integer bookRating,
                                      @RequestParam(name = "bookReview") String bookReview) {
        int status = bookService.createBook(images, bookTitle, bookRating, bookReview);
        ConsoleStatusVo consoleStatusVo = new ConsoleStatusVo();
        consoleStatusVo.setStatus(1 == status ? "成功" : "失败");
        return consoleStatusVo;
    }

    @RequestMapping("/book/update")
    public ConsoleStatusVo bookUpdate(@RequestParam(name = "bookId") BigInteger bookId,
                                      @RequestParam(name = "images") String images,
                                      @RequestParam(name = "bookTitle") String bookTitle,
                                      @RequestParam(name = "bookRating") Integer bookRating,
                                      @RequestParam(name = "bookReview") String bookReview) {
        int status = bookService.updateBook(bookId, images, bookTitle, bookRating, bookReview);
        ConsoleStatusVo consoleStatusVo = new ConsoleStatusVo();
        consoleStatusVo.setStatus(1 == status ? "成功" : "失败");
        return consoleStatusVo;
    }

    @RequestMapping("/book/delete")
    public ConsoleStatusVo bookDelete(@RequestParam(name = "bookId") BigInteger bookId) {
        int status = bookService.deleteBook(bookId);
        ConsoleStatusVo consoleStatusVo = new ConsoleStatusVo();
        consoleStatusVo.setStatus(1 == status ? "成功" : "失败");
        return consoleStatusVo;
    }

}
