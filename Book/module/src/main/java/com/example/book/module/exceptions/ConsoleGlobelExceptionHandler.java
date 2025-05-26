package com.example.book.module.exceptions;

import com.example.book.module.service.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ConsoleGlobelExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("系统异常：{}", e.getMessage(), e);
        return Result.fail("系统异常");
    }
}
