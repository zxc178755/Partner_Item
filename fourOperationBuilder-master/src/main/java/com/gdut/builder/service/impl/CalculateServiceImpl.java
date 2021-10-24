package com.gdut.builder.service.impl;

import com.gdut.builder.model.Fraction;
import com.gdut.builder.service.CalculateService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 分数的计算类
 * 返回一个分数包装类
 */
@Service
public class CalculateServiceImpl implements CalculateService {

    @Override
    public Fraction calculateFra(List fraSymList) {
        Stack tempOperator = new Stack(); // 临时存储运算符和 (
        List operator = new ArrayList(); // 存储数值和运算符

        int len = fraSymList.size(); // 数字和运算符的总长度
        int times = 0; // 遍历次数

        while (times < len) {
            if (fraSymList.get(times) instanceof Fraction) {
                Fraction chara = (Fraction) fraSymList.get(times);
                operator.add(chara);
            } else {
                String chara = (String) fraSymList.get(times);
                switch (chara) {
                    case "(":
                        tempOperator.push(chara); // 将 ( 入栈
                        break;
                    case ")":
                        while (tempOperator.peek() != "(") { // 循环查找 ( 并返回，但不删除
                            operator.add(tempOperator.pop()); // 将除 ) 外的运算符存入 operator 中
                        }
                        tempOperator.pop(); // 弹出 ( ，丢弃括号 ()
                        break;
                    case "+":
                    case "-":
                        while (!tempOperator.empty() && tempOperator.peek() != "(") {
                            operator.add(tempOperator.pop());
                        }
                        tempOperator.push(chara);
                        break;
                    case "*":
                    case "/":
                        while (!tempOperator.empty() && tempOperator.peek().toString().matches("[*/]")) {
                            operator.add(tempOperator.pop());
                        }
                        tempOperator.push(chara);
                        break;
                    default:
                        operator.add(chara);
                        break;
                }
            }
            times++;
        }

        while (!tempOperator.empty()) {
            operator.add(tempOperator.pop());
        }

        Stack<Fraction> tempFrac = new Stack<Fraction>();
        for (int i = 0; i < operator.size(); i++) {
            if (operator.get(i) instanceof Fraction) { // 先将数字存入栈中
                tempFrac.push((Fraction) operator.get(i));
            } else { // 遇到运算符，从栈中取出两个数字进行运算
                Fraction result;
                if (operator.get(i).equals("+")) {
                    result = tempFrac.pop().add(tempFrac.pop());
                } else if (operator.get(i).equals("-")) {
                    Fraction pop1 = tempFrac.pop();
                    Fraction pop2 = tempFrac.pop();
                    result = pop2.sub(pop1);
                    if (result == null) {
                        // 减法运算返回null，说明是负数，直接返回null
                        return null;
                    }
                } else if (operator.get(i).equals("*")) {
                    result = tempFrac.pop().muti(tempFrac.pop());
                } else {
                    Fraction pop1 = tempFrac.pop();
                    Fraction pop2 = tempFrac.pop();
                    result = pop2.div(pop1);
                    if (result == null) {
                        return null;
                    }
                }
                tempFrac.push(result);
            }
        }
        return tempFrac.pop();
    }
}
