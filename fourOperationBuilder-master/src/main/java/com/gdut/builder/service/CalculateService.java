package com.gdut.builder.service;

import com.gdut.builder.model.Fraction;

import java.util.List;

public interface CalculateService {

    /**
     * 中缀转前缀方法
     * @param FraSymList 表达式拆分集合，包含运算数和运算符
     * @return 计算答案
     */
    Fraction calculateFra(List FraSymList);
}
