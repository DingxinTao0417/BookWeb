package com.example.book.console.controller;

import com.example.book.console.domain.*;
import com.example.book.module.dto.BookDto;
import com.example.book.module.dto.CategoryMapDto;
import com.example.book.module.entity.Book;
import com.example.book.module.service.BookService;
import com.example.book.module.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/book/add")
    public ConsoleStatusVo bookCreate(@RequestParam(name = "images") String images,
                                      @RequestParam(name = "bookTitle") String bookTitle,
                                      @RequestParam(name = "bookRating") Integer bookRating,
                                      @RequestParam(name = "bookReview") String bookReview,
                                      @RequestParam(name = "categoryId") BigInteger categoryId ) {
        ConsoleStatusVo consoleStatusVo = new ConsoleStatusVo();
        try {
            if (!bookService.categoryExists(categoryId)) {
                consoleStatusVo.setError("分类不存在");
                return consoleStatusVo;
            }
            BigInteger bookId = bookService.editBook(null, images, bookTitle.trim(), bookRating, bookReview, categoryId);
            consoleStatusVo.setBookId(bookId);
            return consoleStatusVo;
        } catch (RuntimeException e){
            consoleStatusVo.setError(e.getMessage());
            return consoleStatusVo;
        }
    }

    @RequestMapping("/book/update")
    public ConsoleStatusVo bookUpdate(@RequestParam(name = "bookId") BigInteger bookId,
                                      @RequestParam(name = "images") String images,
                                      @RequestParam(name = "bookTitle") String bookTitle,
                                      @RequestParam(name = "bookRating") Integer bookRating,
                                      @RequestParam(name = "bookReview") String bookReview,
                                      @RequestParam(name = "categoryId") BigInteger categoryId) {
        ConsoleStatusVo consoleStatusVo = new ConsoleStatusVo();
        try {
            if (!bookService.categoryExists(categoryId)) {
                consoleStatusVo.setError("分类不存在");
                return consoleStatusVo;
            }
            BigInteger returnedBookId = bookService.editBook(null, images, bookTitle.trim(), bookRating, bookReview, categoryId);
            consoleStatusVo.setBookId(returnedBookId);
            return consoleStatusVo;
        } catch (RuntimeException e){
            consoleStatusVo.setError(e.getMessage());
            return consoleStatusVo;
        }
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
        String categoryName = categoryService.getCategoryNameById(book.getCategoryId());
        if (book == null) {
            return new ConsoleInfoVo();
        }
        ConsoleInfoVo consoleInfoVo = new ConsoleInfoVo();
        consoleInfoVo.setImageList(new ArrayList<>(Arrays.asList(book.getImages().split("\\$"))));
        consoleInfoVo.setBookTitle(book.getBookTitle());
        consoleInfoVo.setBookRating(book.getBookRating());
        consoleInfoVo.setBookReview(book.getBookReview());
        consoleInfoVo.setBookCategory(categoryName);
        int timestamp = book.getCreateTime();
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = dateTime.format(formatter);
        consoleInfoVo.setCreateTime(formattedTime);
        return consoleInfoVo;
    }

    @RequestMapping({"/book/list"})
    public ConsoleListVo bookAll(@RequestParam(name = "page", defaultValue = "1") int page,
                                 @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
    ) {
        int offset = (page - 1) * pageSize;
        List<BookDto> consoleList = this.bookService.getBookWithCategoryByOffset(offset, pageSize);
        if (consoleList == null || consoleList.isEmpty()) {
            return new ConsoleListVo();
        }
        int total = this.bookService.getBookTotal();
        if (total == 0) {
            return new ConsoleListVo();
        }
        List<ConsoleListDetailsVo> consoleListDetailsVoList = new ArrayList();
        Iterator var3 = consoleList.iterator();

        while(var3.hasNext()) {
            BookDto book = (BookDto)var3.next();
            ConsoleListDetailsVo consoleListDetailsVo = new ConsoleListDetailsVo();
            consoleListDetailsVo.setBookId(book.getBookId());
            consoleListDetailsVo.setImage(book.getImages().split("\\$")[0]);
            consoleListDetailsVo.setBookTitle(book.getBookTitle());
            consoleListDetailsVo.setBookRating(book.getBookRating());
            String categoryName = book.getBookCategory();
            if (categoryName == null) {
                consoleListDetailsVo.setBookCategory("未知分类");
            } else {
                consoleListDetailsVo.setBookCategory(categoryName);
            }

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
        consoleListVo.setPage(page);
        consoleListVo.setPageSize(pageSize);
        consoleListVo.setTotal(total);
        return consoleListVo;
    }

}
