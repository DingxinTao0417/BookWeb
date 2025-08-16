package com.example.book.app.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class BookListVo {
    private List<BookListDetailsVo> list;

    @JsonProperty("isEnd")
    private Boolean isEnd;

    private String wp;
}
