package com.example.book.app.controller;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    public BookCategoryListVo categoryAll(@RequestParam(name = "parentId", required = false) BigInteger parentId) {
        List<Category> parentCategories = this.categoryService.getValidCategory(parentId);

        List<BookCategoryDetailsVo> bookCategoryList = new ArrayList();

        List<Category> allCategories = categoryService.getAllCategory();
        Map<BigInteger, List<Category>> categoryMap = allCategories.stream()
                .filter(c -> c.getParentId() != null)
                .collect(Collectors.groupingBy(Category::getParentId));

        for (Category parent : parentCategories) {
            BookCategoryDetailsVo bookCategoryDetailsVo = new BookCategoryDetailsVo();
            bookCategoryDetailsVo.setCategoryId(parent.getId());
            bookCategoryDetailsVo.setCategoryName(parent.getCategoryName());


            List<SubCategoryDetailsVo> subCategoryList = new ArrayList();

            List<Category> subCategories = categoryMap.getOrDefault(parent.getId(), Collections.emptyList());
            for (Category subCategory : subCategories) {
                SubCategoryDetailsVo subCategoryDetailsVo = new SubCategoryDetailsVo();
                subCategoryDetailsVo.setCategoryId(subCategory.getId());
                subCategoryDetailsVo.setCategoryName(subCategory.getCategoryName());
                subCategoryDetailsVo.setCategoryImages(subCategory.getCategoryImages().split("\\$")[0]);
                subCategoryList.add(subCategoryDetailsVo);
            }

            bookCategoryDetailsVo.setSubCategoryDetails(subCategoryList);
            bookCategoryList.add(bookCategoryDetailsVo);
        }

        BookCategoryListVo bookCategoryListVo = new BookCategoryListVo();
        bookCategoryListVo.setList(bookCategoryList);
        return bookCategoryListVo;
    }
}