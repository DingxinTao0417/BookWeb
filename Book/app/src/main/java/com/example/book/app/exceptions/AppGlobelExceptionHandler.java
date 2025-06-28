package com.example.book.app.exceptions;

import com.example.book.module.service.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class AppGlobelExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("网络繁忙：{}", e.getMessage(), e);
        return Result.fail("网络繁忙");
    }
}
