package com.example.noaskqaes.utils;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/7/2 16:08
 */
public class BaseVariableUtils {

    public static void statsTime(String name, long staTime) {
        System.out.println(String.format("%s耗时:%s秒", name, (System.currentTimeMillis() - staTime)));
    }
}
