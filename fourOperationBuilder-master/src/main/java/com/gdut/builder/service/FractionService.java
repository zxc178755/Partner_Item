package com.gdut.builder.service;

import com.gdut.builder.model.Result;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FractionService {

    boolean isRule(List list);

    /**
     * @param result     预添加进List集合的结果类
     * @param resultList 已经添加入集合的结果集合
     * @return true-有相同的运算式 false没有相同的运算式
     */
    boolean checkSame(Result result, List<Result> resultList);
}
