package com.example.book.console.controller;

import com.example.book.console.domain.*;
import com.example.book.module.entity.Book;
import com.example.book.module.entity.Category;
import com.example.book.module.service.BookService;
import com.example.book.module.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.example.book.module.service.Result;

@Slf4j
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/category/add")
    public BigInteger categoryCreate(@RequestParam(name = "images") String images,
                                     @RequestParam(name = "categoryName") String categoryName) {
        ConsoleStatusVo consoleStatusVo = new ConsoleStatusVo();
        try {
            BigInteger returnedCategoryId = categoryService.createCategory(images, categoryName);
            return returnedCategoryId;
        } catch (RuntimeException e){
            return BigInteger.ZERO;
        }
    }

    @RequestMapping("/category/update")
    public ConsoleStatusVo categoryUpdate(@RequestParam(name = "categoryId") BigInteger categoryId,
                                          @RequestParam(name = "images") String images,
                                          @RequestParam(name = "categoryName") String categoryName) {
        ConsoleStatusVo consoleStatusVo = new ConsoleStatusVo();
        try {
            BigInteger returnedCategoryId = categoryService.updateCategory(categoryId, images, categoryName);
            consoleStatusVo.setCategoryId(returnedCategoryId);
            return consoleStatusVo;
        } catch (RuntimeException e){
            consoleStatusVo.setError(e.getMessage());
            return consoleStatusVo;
        }
    }

    @RequestMapping("/category/delete")
    public ConsoleStatusVo categoryDelete(@RequestParam(name = "categoryId") BigInteger categoryId) {
        int status = categoryService.delete(categoryId);
        ConsoleStatusVo consoleInfoVo = new ConsoleStatusVo();
        consoleInfoVo.setStatus(1 == status ? "成功" : "失败");
        return consoleInfoVo;
    }

    @RequestMapping({"/category/list"})
    public ConsoleCategoryListVo categoryAll(@RequestParam(name = "page", defaultValue = "1") int page,
                                             @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        int offset = (page - 1) * pageSize;
        List<Category> categoryList = this.categoryService.getAllCategory();

        List<ConsoleCategoryDetailsVo> bookCategoryVoList = new ArrayList();
        Iterator var3 = categoryList.iterator();

        while(var3.hasNext()) {
            Category category = (Category)var3.next();
            ConsoleCategoryDetailsVo categoryDetailsVo = new ConsoleCategoryDetailsVo();
            categoryDetailsVo.setCategoryId(category.getId());
            categoryDetailsVo.setCategoryName(category.getCategoryName());
            categoryDetailsVo.setCategoryImages(category.getCategoryImages().split("\\$")[0]);
            categoryDetailsVo.setParentId(category.getParentId());

            bookCategoryVoList.add(categoryDetailsVo);
        }

        ConsoleCategoryListVo bookCategoryListVo = new ConsoleCategoryListVo();
        bookCategoryListVo.setList(bookCategoryVoList);
        return bookCategoryListVo;
    }

}
