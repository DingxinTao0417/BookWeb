package com.example.book.app.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class BookInfoVo {
    private List<String> imageList;
    private String createTime;
    private String bookTitle;
    private Integer bookRating;
    private String bookReview;
    private String bookCategory;
}