package com.example.book.app.controller;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.example.book.app.domain.*;
import com.example.book.app.exceptions.AppGlobelExceptionHandler;
import com.example.book.module.entity.Book;
import com.example.book.module.entity.Category;
import com.example.book.module.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    private List<String> generateList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add("1");
        }
        return list;
    }

    @RequestMapping("/test/unsafe")
    public String testUnsafe(){
        List<String> list = generateList();
        StringBuilder sb = new StringBuilder();

        // 启动 10 个线程，每个线程都拼接整个 list
        Thread[] threads = new Thread[10];
        for (int t = 0; t < 10; t++) {
            threads[t] = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < list.size(); i++) {
                        sb.append(list.get(i)).append(",");
                    }
                }
            });
            threads[t].start();
        }

        // 等待所有线程执行完成
        for (int t = 0; t < 10; t++) {
            try {
                threads[t].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 返回拼接后的长度
        return "StringBuilder length (not thread safe): " + sb.length();
    }

    @RequestMapping("/test/safe")
    public String testSafe() {
        List<String> list = generateList();
        StringBuffer sb = new StringBuffer();

        List<Thread> threads = new ArrayList<Thread>();
        for (int t = 0; t < 10; t++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < list.size(); i++) {
                        sb.append(list.get(i)).append(",");
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }

// 等所有线程结束
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "StringBuffer length (thread safe): " + sb.length();
    }
}
