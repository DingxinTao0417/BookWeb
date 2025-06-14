package com.example.book.console.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ConsoleCategoryListVo {
    private List<ConsoleCategoryDetailsVo> list;
}
