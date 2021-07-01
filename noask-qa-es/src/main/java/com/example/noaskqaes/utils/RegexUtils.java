package com.example.noaskqaes.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/30 16:54
 */
public class RegexUtils {
    public static void main(String str, String pattern) {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        System.out.println(m.matches());
    }
}
