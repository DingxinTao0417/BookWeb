package com.example.book.module.generator;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

import java.math.BigInteger;

public class BigIntegerTypeConvert extends MySqlTypeConvert {
    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        if (fieldType.toLowerCase().contains("bigint")) {
            return DbColumnType.BIG_INTEGER;
        }
        return super.processTypeConvert(globalConfig, fieldType);
    }
}