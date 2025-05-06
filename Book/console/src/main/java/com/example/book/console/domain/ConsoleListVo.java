package com.example.book.console.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ConsoleListVo {
    private List<ConsoleListDetailsVo> list;
    private int page;
    private int pageSize;
    private int total;
}
