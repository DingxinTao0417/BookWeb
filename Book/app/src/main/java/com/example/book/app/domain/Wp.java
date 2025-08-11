package com.example.book.app.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Wp {
    private Integer page;
    private String keyword;
}
