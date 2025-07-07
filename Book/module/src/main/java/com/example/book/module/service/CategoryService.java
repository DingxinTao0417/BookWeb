package com.example.book.module.service;

import com.example.book.module.entity.Category;
import com.example.book.module.mapper.BookMapper;
import com.example.book.module.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

/**
* <p>
    * 分类 Service 类（无接口）
    * </p>
*
* @author DingxinTao
* @since 2025-06-23
*/
@Service
public class CategoryService {

    @Resource
    private BookMapper bookMapper;

    @Resource
    private CategoryMapper categoryMapper;

    public BigInteger createCategory(String images, String categoryName) {
        Category category = new Category();
        category.setCategoryImages(images);
        category.setCategoryName(categoryName);
        category.setIsDeleted(0);

        categoryMapper.insert(category);
        return category.getId();
    }

    public BigInteger updateCategory(BigInteger categoryId, String images, String categoryName) {
        Category category = new Category();
        category.setCategoryImages(images);
        category.setCategoryName(categoryName);

        if (categoryMapper.getCategoryNameById(categoryId) == null) {
            throw new RuntimeException("书籍不存在");
        }
        category.setId(categoryId);
        categoryMapper.update(category);
        return category.getId();
    }

    public int delete(BigInteger id) {
        if (id == null) {
            return 0;
        }
        int time = (int) (System.currentTimeMillis() / 1000);
        bookMapper.deleteByCategoryId(id, time);
        return categoryMapper.delete(id);
    }

    public String getCategoryNameById(BigInteger id){
        return categoryMapper.getCategoryNameById(id);
    }

    public List<Category> getAllCategory(){
        return categoryMapper.getAllCategory();
    }

    public List<Category> getByOffset(Integer offset, Integer limit){
        return categoryMapper.getByOffset(offset, limit);
    }

}
