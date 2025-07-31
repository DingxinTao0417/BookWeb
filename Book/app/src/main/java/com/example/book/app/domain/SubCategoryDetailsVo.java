package com.example.book.app.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class SubCategoryDetailsVo {
    private BigInteger categoryId;
    private String categoryName;
    private String categoryImages;
}
