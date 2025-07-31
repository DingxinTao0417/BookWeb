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

import com.example.book.app.domain.*;
import com.example.book.module.entity.Book;
import com.example.book.module.entity.Category;
import com.example.book.module.service.BookService;
import com.example.book.module.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    public CategoryController() {
    }

    @RequestMapping({"/category/list"})
    public BookCategoryListVo categoryAll() {
        List<Category> categoryList= this.categoryService.getValidCategory();

        List<BookCategoryDetailsVo> bookCategoryVoList = new ArrayList();
        Iterator var3 = categoryList.iterator();

        while(var3.hasNext()) {
            Category category = (Category)var3.next();
            BookCategoryDetailsVo categoryDetailsVo = new BookCategoryDetailsVo();
            categoryDetailsVo.setCategoryId(category.getId());
            categoryDetailsVo.setCategoryName(category.getCategoryName());

            //categoryDetailsVo.setCategoryImages(category.getCategoryImages().split("\\$")[0]);
            //categoryDetailsVo.setParentId(category.getParentId());
            bookCategoryVoList.add(categoryDetailsVo);
        }

        BookCategoryListVo bookCategoryListVo = new BookCategoryListVo();
        bookCategoryListVo.setList(bookCategoryVoList);
        return bookCategoryListVo;
    }
}