package com.example.designdemo.demo1;

/**
 * @author zhou lin
 * @description 简单实现 计算器
 * @create 2021-07-20 20:18
 */
public class EasyMain {
    public static void main(String[] args) {
        Double count = count(1d, 2d, "+");
//        Double count = count(1d, 0, "/");
        System.out.println("结果：" + count);
    }

    private static Double count(double valueA, double valueB, String operation) {
        double result;
        switch (operation) {
            case "+":
                result = valueA + valueB;
                break;
            case "-":
                result = valueA + valueB;
                break;
            case "*":
                result = valueA * valueB;
                break;
            case "/":
                if (valueB == 0) {
                    throw new RuntimeException("除数不能为0");
                }
                result = valueA / valueB;
                break;
            default:
                result = Double.parseDouble("");
                break;
        }
        return result;
    }
}
