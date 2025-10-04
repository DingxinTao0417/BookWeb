package com.example.book.module.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class BookDto {
    private BigInteger bookId;
    private String images;
    private String bookTitle;
    private Integer bookRating;
    private String bookCategory;
    private Integer createTime;
    private Integer updateTime;
}
