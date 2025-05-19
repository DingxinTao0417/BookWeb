package com.example.book.module.service;
import lombok.Data;

@Data
public class Result<T> {
    private boolean status;
    private String message;
    private T result;
    private String errorMessage;

    public Result() {}

    public static <T> Result<T> success(T result, String message) {
        Result<T> r = new Result<>();
        r.status = true;
        r.result = result;
        r.message = message;
        return r;
    }

    public static <T> Result<T> fail(String message) {
        Result<T> r = new Result<>();
        r.status = false;
        r.message = message;
        return r;
    }

    public static <T> Result<T> fail(String message, String errorMessage) {
        Result<T> r = new Result<>();
        r.status = false;
        r.message = message;
        r.errorMessage = errorMessage;
        return r;
    }
}
