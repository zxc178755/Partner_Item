package com.gdut.builder.service;


import com.gdut.builder.model.Result;

import java.util.List;

public interface GenerateService {

    /**
     * 生成算式
     *
     * @param limit 生成每一个算式的最大值
     * @return 算式表达式
     */
    Result generate(int limit);

    /**
     * 根据两个规定的入参生成结果集合
     * @param questionNum 题目数量
     * @param maxLimit 题目中最大的数值
     * @return 运算式+结果 List集合
     */
    List<Result> generateList(int questionNum, int maxLimit);
}
