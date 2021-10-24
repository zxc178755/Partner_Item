package com.gdut.builder.service.impl;


import com.gdut.builder.model.Fraction;
import com.gdut.builder.model.Result;
import com.gdut.builder.service.CalculateService;
import com.gdut.builder.service.FractionService;
import com.gdut.builder.service.GenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GenerateServiceImpl implements GenerateService {

    @Autowired
    private FractionService fractionService;

    @Autowired
    private CalculateService calculateService;

    /*
     * 生成算式，以及结果 limit表示生成每一个分式的最大值
     */
    public Result generate(int maxLimit) {
        Random r = new Random();

        char[] c = {'+', '-', '*', '÷'};
        // 生成运算符的数量，题目要求不超过3个。这里范围为1~3
        int operaCount = r.nextInt(3) + 1;
        // 分数的个数，根据运算符个数来生成。例如1个运算符最多有两个分数
        int fractionCount = r.nextInt(operaCount + 1) + 1;
        // 非分数的个数 = 运算符个数 + 1 - 分数的个数
        int unFractionCount = operaCount + 1 - fractionCount;
        // 存放分数的List集合
        List<Fraction> fractionList = new ArrayList<>();
        // 存放符号的List集合
        List<String> symbolList = new ArrayList<>();
        // 存放运算数和运算符
        List fraSymList = new ArrayList();
        for (int i = 0; i < operaCount; i++) {
            // 遍历运算符个数，一个运算符生成一个数
            symbolList.add(String.valueOf(c[r.nextInt(4)]));
        }
        for (int i = 0; i < fractionCount; i++) {
            // 遍历分数的个数
            fractionList.add(new Fraction(true, maxLimit));
        }
        // 剩下的就是非分数
        if (unFractionCount >= 0) {
            for (int i = 0; i < unFractionCount; i++) {
                fractionList.add(new Fraction(false, maxLimit));
            }
        }

        // 运算数的个数 = 符号个数 + 1 即 fractionList.size() = symbolList.size() + 1
        // 因此取其一遍历即可
        int j = 0;
        for (int i = 0; i < symbolList.size(); i++) {
            Fraction fraction = fractionList.get(i);
            String symbol = symbolList.get(i);
            fraSymList.add(fraction);
            fraSymList.add(symbol);
            j++;
        }
        // 拼接最后一个运算数
        Fraction lastFraction = fractionList.get(j);
        fraSymList.add(lastFraction);

        // 将除号转换为计算用的/
        for (int i = 0; i < fraSymList.size(); i++) {
            if (i % 2 != 0) {
                if (fraSymList.get(i).equals("÷")) {
                    fraSymList.set(i, "/");
                }
            }
        }
        if (!fractionService.isRule(fraSymList)) {
            // 不符合规则，返回null
            return null;
        }
        List newFraSymList = addBracket(fraSymList);
        Fraction resultFra = calculateService.calculateFra(newFraSymList);

        if (resultFra == null) {
            return null;
        }
        String expression = "";
        for (int i = 0;i<newFraSymList.size();i++) {
            if (newFraSymList.get(i) instanceof Fraction) {
                Fraction fraction =(Fraction) newFraSymList.get(i);
                // 如果是运算数
                expression = expression + fraction.toString() + " ";
            } else {
                // 如果是运算符
                expression = expression + newFraSymList.get(i) + " ";
            }
        }
        String trimExpression = expression.trim();
        return new Result(trimExpression, resultFra.toString());
    }

    @Override
    public List<Result> generateList(int questionNum, int maxLimit) {
        // 写入到文件的缓冲流
        BufferedOutputStream bo;
        BufferedOutputStream bo2;
        try {
            File file = new File("./Exercises.txt");
            File file2 = new File("./Answers.txt");
            bo = new BufferedOutputStream(new FileOutputStream(file));
            bo2 = new BufferedOutputStream(new FileOutputStream(file2));
            List<Result> resultList = new ArrayList<>();
            int i = 1;
            while (i <= questionNum) {
                // 生成questionNum个结果
                Result result = generate(maxLimit);
                if (result == null) {
                    continue;
                }
                // 设置题目编号
                result.setNumber(i);
                // 判断题目是否一样
                boolean isSame = fractionService.checkSame(result, resultList);
                if (isSame) {
                    // 如果相同，跳过该运算式
                    continue;
                }
                resultList.add(result);

                // 将四则运算题目+答案 输出到 txt文件中
                bo.write((i+". ").getBytes());
                bo.write(result.toStringExp().getBytes());
                bo.write("\r\n".getBytes());

                bo2.write((i+". ").getBytes());
                bo2.write(result.getAnswer().getBytes());
                bo2.write("\r\n".getBytes());
                i++;
            }
            bo.flush();
            bo2.flush();
            bo.close();
            bo2.close();
            return resultList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.out.println("文件输入输出异常");
            e.printStackTrace();
            return null;
        }
    }

    private List addBracket(List fractionList) {
        Random r = new Random();
        // 运算数+运算符的长度 必为奇数
        int size = fractionList.size();
        int addIndex;
        int addDiff;
        if (size == 3) {
            // 如果总计3个，则没有运算顺序可言
            return fractionList;
        } else if (size == 5) {
            // 则有三种情况 1+2+3 （1+2）+3 1+(2+3)
            int i = r.nextInt(3);
            if (i == 0) {
                // 不加括号
                return fractionList;
            } else if (i == 1) {
                addIndex = 0;
                addDiff = 4;
            } else {
                addIndex = 2;
                addDiff = 4;
            }
        } else {
            // 剩下就是7个
            // 两个括号情况：4个运算数 12 13 23 24 34
            int i = r.nextInt(6);
            if (i == 0) {
                return fractionList;
            } else if (i == 1) {
                // 12
                addIndex = 0;
                addDiff = 4;
            } else if (i == 2) {
                // 13
                addIndex = 0;
                addDiff = 6;
            } else if (i == 3) {
                // 23
                addIndex = 2;
                addDiff = 4;
            } else if (i == 4) {
                // 24
                addIndex = 2;
                addDiff = 6;
            } else {
                // 34
                addIndex = 4;
                addDiff = 4;
            }
        }
        fractionList.add(addIndex, "(");
        fractionList.add(addIndex + addDiff, ")");
        return fractionList;
    }

}
