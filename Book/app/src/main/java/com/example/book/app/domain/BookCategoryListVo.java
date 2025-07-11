package com.example.book.app.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class BookCategoryListVo {
    private List<BookCategoryDetailsVo> list;
}
