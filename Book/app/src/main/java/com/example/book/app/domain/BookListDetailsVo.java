package com.example.book.app.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class BookListDetailsVo {
    private BigInteger bookId;
    private String image;
    private String bookTitle;
    private Integer bookRating;
    private String bookCategory;
}