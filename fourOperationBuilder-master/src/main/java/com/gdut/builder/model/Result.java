package com.gdut.builder.model;

/*
 * 运算式+运算结果 包装类
 */
public class Result {

    // 题目编号
    private Integer number;
    // 运算表达式
    private String exp;
    // 运算结果
    private String answer;

    public Result(String exp, String answer) {
        super();
        this.exp = exp;
        this.answer = answer;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return exp + " = " + answer;
    }

    public String toStringExp() {
        return exp + " = ";
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}
