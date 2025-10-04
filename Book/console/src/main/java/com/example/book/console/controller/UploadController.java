package com.example.book.console.controller;

import com.example.book.console.domain.ImageAddressVo;
import com.example.book.module.entity.Book;
import com.example.book.module.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigInteger;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {

    @Autowired
    private BookService bookService;

    private static final String UPLOAD_DIR = "/Users/taodingxin/Documents/images";

    @RequestMapping("/upload/image")
    public ImageAddressVo uploadImage(@RequestParam(name = "bookId") BigInteger bookId,
                                      @RequestParam("file") MultipartFile file) {
        ImageAddressVo imageAddressVo = new ImageAddressVo();

        Book book = bookService.getBookInfoById(bookId);
        if (book == null) {
            imageAddressVo.setImageAddress("");
            return imageAddressVo;
        }

        if (file.isEmpty()) {
            imageAddressVo.setStatus(false);
            imageAddressVo.setImageAddress("未上传图片");
            return imageAddressVo;
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + ext;
            File dest = new File(UPLOAD_DIR, fileName);
            file.transferTo(dest);

            String relativePath = UPLOAD_DIR + fileName;

            String oldImages = book.getImages();
            String newImages;
            if (oldImages == null || oldImages.isEmpty()) {
                newImages = relativePath;
            } else {
                newImages = oldImages + "$" + relativePath;
            }

            bookService.updateBookImage(bookId, newImages);

            imageAddressVo.setStatus(true);
            imageAddressVo.setImageAddress(relativePath);

        } catch (Exception e) {
            imageAddressVo.setStatus(false);
            imageAddressVo.setMessage("上传失败: " + e.getMessage());
        }

        return imageAddressVo;
    }
}
