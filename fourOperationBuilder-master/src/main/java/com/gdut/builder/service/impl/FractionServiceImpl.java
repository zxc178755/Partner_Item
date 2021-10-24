package com.gdut.builder.service.impl;

import com.gdut.builder.model.Fraction;
import com.gdut.builder.model.Result;
import com.gdut.builder.service.FractionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


@Service
public class FractionServiceImpl implements FractionService {

    /*
     * 判断除数是否为0，判断分数是否为0
     * 不合规则返回false
     */
    public boolean isRule(List list) {

        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                // 整除2，说明是运算数（分数）
                Fraction fraction = (Fraction) list.get(i);
                if (fraction.getDenominator() == 0) {
                    // 分母为0
                    return false;
                }
            } else {
                // 是运算符
                String flag = (String) list.get(i);
                if ("/".equals(flag)) {
                    // 是除号的话，取下一个运算数，判断是否为0
                    Fraction fraction = (Fraction) list.get(i + 1);
                    if (fraction.getDenominator() == 0 || fraction.getNominator() == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param result     预添加进List集合的结果类
     * @param resultList 已经添加入集合的结果集合
     * @return true-有相同的运算式 false没有相同的运算式
     */
    public boolean checkSame(Result result, List<Result> resultList) {
        if (resultList == null || resultList.size() == 0) {
            return false;
        }
        String answer = result.getAnswer();
        for (Result result1 : resultList) {
            String answer1 = result1.getAnswer();
            if (answer.equals(answer1)) {
                // 如果答案相同，则判断运算式是否完全一致
                boolean isSame = isRepeat(convertExpToList(result.getExp()), convertExpToList(result1.getExp()));
                if (isSame) {
                    // 如果相同
                    return true;
                }
            }
        }
        // 结束循环还未返回，说明不重复
        return false;
    }

    /**
     * 查重，如果运算数和运算符完全一致则判断其为相同
     *
     * @param sameAnswerExp 相同答案的表达式
     * @param waitIntoExp   待加入到集合中的表达式
     * @return true-重复 false-不重复
     */
    private boolean isRepeat(List<String> sameAnswerExp, List<String> waitIntoExp) {
        if (sameAnswerExp == null || waitIntoExp == null) {
            // 返回true可以在上一层方法中跳过这个表达式
            return true;
        }
        if (sameAnswerExp.size() != waitIntoExp.size()) {
            // 长度不相同，则说明题目是不一样的
            return false;
        }
        // 遍历待判断的运算式
        Iterator<String> iterator = waitIntoExp.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            // 遍历预判断的表达式
            if (sameAnswerExp.contains(iterator.next())) {
                // 计算相同符号的个数
                i = i + 1;
            }
        }
        if (i == waitIntoExp.size()) {
            return true;
        }
        return false;
    }

    private List<String> convertExpToList(String exp) {
        if (exp == null || exp.length() == 0) {
            // 如果表达式长度为0
            return null;
        }
        // 根据空格分割运算数和运算符
        String[] StrArray = exp.split(" ");
        return new ArrayList<>(Arrays.asList(StrArray));
    }

}
