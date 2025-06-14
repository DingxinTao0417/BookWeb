package com.example.book.console.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class ConsoleCategoryDetailsVo {
    private BigInteger categoryId;
    private String categoryName;
    private String categoryImages;
}
