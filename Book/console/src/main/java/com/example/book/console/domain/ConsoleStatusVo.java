package com.example.book.console.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class ConsoleStatusVo {
    private BigInteger bookId;
    private BigInteger categoryId;
    private String error;
    private String status;
}
