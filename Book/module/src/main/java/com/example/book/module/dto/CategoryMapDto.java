package com.example.book.module.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class CategoryMapDto {
    private BigInteger id;
    private String categoryName;
}
