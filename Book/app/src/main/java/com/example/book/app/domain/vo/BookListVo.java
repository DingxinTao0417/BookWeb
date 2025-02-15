package com.example.book.app.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class BookListVo {
    private List<BookListDetailsVo> list;
}
