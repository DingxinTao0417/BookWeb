package com.example.book.module.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class Category {
    private BigInteger id;
    private String categoryName;
    private String categoryImages;
}
