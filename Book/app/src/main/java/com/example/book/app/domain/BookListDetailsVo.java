package com.example.book.app.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class BookListDetailsVo {
    private int bookId;
    private String image;
    private String bookTitle;
    private Integer bookRating;
}