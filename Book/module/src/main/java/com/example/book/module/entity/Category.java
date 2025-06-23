package com.example.book.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.math.BigInteger;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分类
 * </p>
 *
 * @author DingxinTao
 * @since 2025-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Category implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private BigInteger id;

    /**
     * 分类封面
     */
    private String categoryImages;

    /**
     * 分类名称
     */
    private String categoryName;

    private Integer isDeleted;


}
