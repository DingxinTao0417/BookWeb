package com.example.book.console.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ConsoleInfoVo {
    private List<String> imageList;
    private String createTime;
    private String bookTitle;
    private Integer bookRating;
    private String bookReview;
}
