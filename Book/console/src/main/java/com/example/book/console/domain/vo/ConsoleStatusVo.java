package com.example.book.console.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConsoleStatusVo {
    private String status;
}
