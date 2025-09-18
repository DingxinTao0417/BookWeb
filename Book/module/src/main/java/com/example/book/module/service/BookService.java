package com.example.book.module.service;

import com.example.book.module.DTO.BookDto;
import com.example.book.module.entity.Book;
import com.example.book.module.entity.Category;
import com.example.book.module.mapper.BookMapper;
import com.example.book.module.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class BookService {

    @Resource
    private BookMapper bookMapper;

    @Autowired
    private CategoryService categoryService;

    public Book getBookInfoById(BigInteger id) {
        if (id == null) {
            return null;
        }
        Book book = bookMapper.getById(id);
        if (book == null) {
            return null;
        }

        String categoryName = categoryService.getCategoryNameById(book.getCategoryId());
        Book bookReturn = new Book();
        bookReturn.setId(book.getId());
        bookReturn.setImages(book.getImages());
        bookReturn.setBookTitle(book.getBookTitle());
        bookReturn.setBookRating(book.getBookRating());
        bookReturn.setBookReview(book.getBookReview());
        bookReturn.setCreateTime(book.getCreateTime());
        bookReturn.setUpdateTime(book.getUpdateTime());
        bookReturn.setBookCategory(categoryName);

        return bookReturn;
    }

    public Book extractBookInfoById(BigInteger id) {
        if (id == null) {
            return null;
        }
        Book book = bookMapper.extractById(id);
        if (book == null) {
            return null;
        }

        String categoryName = categoryService.getCategoryNameById(book.getCategoryId());
        Book bookReturn = new Book();
        bookReturn.setId(book.getId());
        bookReturn.setImages(book.getImages());
        bookReturn.setBookTitle(book.getBookTitle());
        bookReturn.setBookRating(book.getBookRating());
        bookReturn.setBookReview(book.getBookReview());
        bookReturn.setCreateTime(book.getCreateTime());
        bookReturn.setUpdateTime(book.getUpdateTime());
        bookReturn.setBookCategory(categoryName);

        return bookReturn;
    }

    public List<Book> getAllBookInfo() {
        return bookMapper.getAll();
    }

    public List<Category> getAllCategory()  {return categoryService.getAllCategory();}

    public String getCategoryNameById(BigInteger id) {return categoryService.getCategoryNameById(id);}

    public List<Book> getBookPageByOffset(int offset, int limit) {
        if (offset < 0 || limit < 0) {
            return null;
        }
        return bookMapper.getByOffset(offset, limit, "", "");
    }

    public List<Book> getBookPageByOffset(int offset, int limit, String keyword) {
        if (offset < 0 || limit < 0 || keyword == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        List<Long> categoryIds = categoryService.getCategoryIds(keyword);
        for (int i = 0; i < categoryIds.size(); i++) {
            sb.append(categoryIds.get(i));
            if (i < categoryIds.size() - 1) {
                sb.append(",");
            }
        }
        String ids = sb.toString();
        return bookMapper.getByOffset(offset, limit, keyword, ids);
    }

    public List<BookDto> getBookWithCategoryByOffset(int offset, int limit) {
        if (offset < 0 || limit < 0) {
            return null;
        }
        return bookMapper.getBookWithCategory(offset, limit, "", "");
    }

    public List<BookDto> getBookWithCategoryByOffset(int offset, int limit, String keyword) {
        if (offset < 0 || limit < 0 || keyword == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        List<Long> categoryIds = categoryService.getCategoryIds(keyword);
        for (int i = 0; i < categoryIds.size(); i++) {
            sb.append(categoryIds.get(i));
            if (i < categoryIds.size() - 1) {
                sb.append(",");
            }
        }
        String ids = sb.toString();
        return bookMapper.getBookWithCategory(offset, limit, keyword, ids);
    }

    public int getBookTotal() {
        return bookMapper.getTotal();
    }

    public List<Category> getCategoryPageByOffset(int offset, int limit) {
        if (offset < 0 || limit < 0) {
            return null;
        }
        return categoryService.getByOffset(offset, limit);
    }

    public int getCategoryTotal() {
        return categoryService.getTotal();
    }


    public Result<Integer> createBook(String images, String bookTitle, Integer bookRating, String bookReview) {
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        Book book = new Book();
        book.setImages(images);
        book.setBookTitle(bookTitle);
        book.setBookRating(bookRating);
        book.setBookReview(bookReview);
        book.setCreateTime(timestamp);
        book.setUpdateTime(timestamp);
        book.setIsDeleted(0);

        try {
            int result = bookMapper.edit(book);
            if (result == 1) {
                return Result.success(result, "添加数据成功");
            } else {
                return Result.fail("添加数据失败");
            }
        } catch (Exception e) {
            return Result.fail("添加数据失败", e.getMessage());
        }
    }

    public Result<Integer> updateBook(BigInteger bookId, String images, String bookTitle, Integer bookRating, String bookReview) {
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        Book book = new Book();
        book.setId(bookId);
        book.setImages(images);
        book.setBookTitle(bookTitle);
        book.setBookRating(bookRating);
        book.setBookReview(bookReview);
        book.setUpdateTime(timestamp);

        try {
            int result =  bookMapper.edit(book);
            if (result == 1) {
                return Result.success(result, "更新数据成功");
            } else {
                return Result.fail("更新数据失败：bookId不存在");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Result.fail("更新数据失败", e.getMessage());
        }
    }

    private void validateParameters(String images, String bookTitle,
                                    Integer bookRating, String bookReview) {
        if (images == null || images.trim().isEmpty()) {
            throw new RuntimeException("图片不能为空");
        }
        if (bookTitle == null || bookTitle.trim().isEmpty()) {
            throw new RuntimeException("书名不能为空");
        }
        if (bookRating == null || bookRating < 0 || bookRating > 5) {
            throw new RuntimeException("评分必须在0-5之间且不能为空");
        }
        if (bookReview == null || bookReview.trim().isEmpty()) {
            throw new RuntimeException("书评不能为空");
        }
    }

    public BigInteger editBook(BigInteger bookId, String images, String bookTitle, Integer bookRating, String bookReview, BigInteger categoryId) {
        validateParameters(images, bookTitle, bookRating, bookReview);

        //if (categoryMapper.getCategoryNameById(categoryId) == null) {
        //    throw new RuntimeException("分类不存在");
        //}

        int timestamp = (int) (System.currentTimeMillis() / 1000);
        Book book = new Book();
        book.setImages(images);
        book.setBookTitle(bookTitle);
        book.setBookRating(bookRating);
        book.setBookReview(bookReview);
        book.setUpdateTime(timestamp);
        //book.setBookCategory(categoryMapper.getCategoryNameById(book.getCategoryId()));
        book.setCategoryId(categoryId);

        if (bookId == null) {
            book.setCreateTime(book.getUpdateTime());
            book.setIsDeleted(0);
            bookMapper.edit(book);
            return book.getId();
        } else {
            if (bookMapper.getById(bookId) == null) {
                throw new RuntimeException("书籍不存在");
            }
            book.setId(bookId);
            bookMapper.edit(book);
            return bookId;
        }
    }

    public void updateBookImage(BigInteger bookId, String images) {
        bookMapper.updateBookImage(bookId, images);
    }

    public boolean categoryExists(BigInteger categoryId) {
        if ((categoryService.getCategoryNameById(categoryId) != null) || (categoryService.getCategoryNameById(categoryId).isEmpty())) {
            return true;
        }
        return false;
    }

    public int deleteBook(BigInteger id) {
        if (id == null) {
            return 0;
        }
        return bookMapper.delete(id, (int) (System.currentTimeMillis() / 1000));
    }

    public void deleteByCategoryId(BigInteger categoryId, Integer time) {
        bookMapper.deleteByCategoryId(categoryId, time);
    }

}
