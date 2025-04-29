package com.example.book.console.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class ConsoleListDetailsVo {
    private BigInteger bookId;
    private String image;
    private String bookTitle;
    private Integer bookRating;
    private String createTime;
    private String updateTime;
}
