package com.example.book.module.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class Book {
    private BigInteger id;
    private String images;
    private String bookTitle;
    private BigInteger categoryId;
    private String bookCategory;
    private Integer bookRating;
    private String bookReview;
    private Integer createTime;
    private Integer updateTime;
    private Integer isDeleted;

}
