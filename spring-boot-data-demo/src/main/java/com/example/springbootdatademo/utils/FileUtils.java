package com.example.springbootdatademo.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FileUtils {

    /**
     * 读取文件
     * @param path 文件名
     * @param clazz 转换对象
     * @return 文件内容
     */
    public static <T> List<T> readFileJava(String path, Class<T> clazz) {
        String data = read(path);
        JSONObject zbJson = JSONObject.parseObject(data);
        JSONArray zb11 = zbJson.getJSONArray(path);
        List<T> list = zb11.stream().map(m -> JSONObject.parseObject(m.toString()).toJavaObject(clazz)).
                collect(Collectors.toList());
        return list;
    }

    /**
     * 读取文件
     * @param path 文件名
     * @return 文件内容
     */
    public static String read(String path) {
        ClassPathResource cpr = new ClassPathResource(path);
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = cpr.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String data;
            while ((data = br.readLine()) != null) {
                sb.append(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
