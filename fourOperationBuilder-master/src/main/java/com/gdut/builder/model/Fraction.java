package com.gdut.builder.model;

import java.util.Random;

public class Fraction {

    // 分母
    private int denominator;
    // 分子
    private int nominator;

    // 构建一个分数
    public Fraction(int denominator, int nominator) {
        super();
        this.denominator = denominator;
        this.nominator = nominator;
    }

    public Fraction(int nominator) {
        this.denominator = 1;
        this.nominator = nominator;
    }

    public Fraction() {
        super();
    }

    // 判断构建的是一个分数还是一个整数，不超过limit的数值
    public Fraction(boolean isFraction, int limit) {
        Random r = new Random();
        // 这是一个分数
        if (isFraction) {
            int den = r.nextInt(limit);
            int nom = r.nextInt(limit);
            while (den == 0) {
                // 当分母为0时重新生成
                den = r.nextInt(limit);
            }
            this.denominator = den;
            this.nominator = nom;
        } else {
            // 这是一个整数
            int nom = r.nextInt(limit);
            this.denominator = 1;
            this.nominator = nom;
        }
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    public int getNominator() {
        return nominator;
    }

    public void setNominator(int nominator) {
        this.nominator = nominator;
    }


    // 加法运算
    public Fraction add(Fraction r) {
        int nom = r.getNominator(); // 分子
        int den = r.getDenominator(); // 分母
        int newNominator = nominator * den + denominator * nom;
        int newDenominator = denominator * den;
        return new Fraction(newDenominator, newNominator);
    }

    // 减法运算
    public Fraction sub(Fraction r) {
        int nom = r.getNominator(); // 分子
        int den = r.getDenominator(); // 分母
        int newNominator = nominator * den - denominator * nom;
        if (newNominator < 0) {
            // 如果分子小于0，说明是负数
            return null;
        }
        int newDenominator = denominator * den;
        return new Fraction(newDenominator, newNominator);
    }

    // 分数的乘法运算
    public Fraction muti(Fraction r) {
        int nom = r.getNominator(); // 分子
        int den = r.getDenominator(); // 分母
        int newNominator = nominator * nom;
        int newDenominator = denominator * den;
        return new Fraction(newDenominator, newNominator);
    }

    // 分数除法运算
    public Fraction div(Fraction r) {
        int nom = r.getNominator(); // 分子
        if (nom == 0) {
            // 分子是0
            return null;
        }
        int den = r.getDenominator(); // 分母
        int newNominator = nominator * den;
        int newDenominator = denominator * nom;
        return new Fraction(newDenominator, newNominator);
    }

    // 用辗转相除法求最大公约数
    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    // 对分数进行约分
    public void Appointment() {
        // 如果分子是0或分母是1就不用约分了
        if (nominator == 0 || denominator == 1)
            return;
        long gcd = gcd(nominator, denominator);
        this.nominator /= gcd;
        this.denominator /= gcd;
    }

    public int isPositive() {
        if (this.nominator < 0 || this.denominator < 0) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 要求：真分数二又八分之三表示为2’3/8
     *
     * @return 分数类的显示形式：整数/真分数/假分数
     */
    @Override
    public String toString() {
        // 先约分
        Appointment();
        if (this.denominator == 1 || this.nominator == 0) {
            // 分母为1或分子为0，直接返回分子的值
            return "" + this.nominator;
        } else if (this.nominator > this.denominator) {
            // 分子大于分母，为假分数，转换为要求低额形式
            if (nominator % denominator == 0) {
                // 能整除，直接返回对应的整数值字符串
                return "" + nominator / denominator;
            }
            // 例子：真分数二又八分之三表示为2’3/8
            return "" + nominator / denominator + "’" + nominator % denominator + "/" + denominator;
        } else {
            // 不是假分数和整数，则直接返回真分数的形式
            return "" + this.nominator + "/" + this.denominator;
        }
    }
}
