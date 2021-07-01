package com.example.noaskqaes.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class FileUtils {

    /**
     * 读取文件
     *
     * @param path  文件名
     * @param clazz 转换对象
     * @return 文件内容
     */
    public static <T> List<T> readFileJava(String path, Class<T> clazz) {
        String data = read(path);
        JSONObject zbJson = JSONObject.parseObject(data);
        JSONArray zb11 = zbJson.getJSONArray("data");
        List<T> list = zb11.stream().map(m -> JSONObject.parseObject(m.toString()).toJavaObject(clazz)).
                collect(Collectors.toList());
        return list;
    }


    public static Map<String, String> readMap(String path, String kName, String vName) {
        JSONArray array = readArray(path);
        return array.stream().collect(Collectors.toMap(
                k -> JSON.parseObject(k.toString()).getString(kName),
                v -> JSON.parseObject(v.toString()).getString(vName)));
    }
    public static JSONArray readArray(String path) {
        String read = read(path);
        return JSON.parseArray(read);
    }

    /**
     * 读取文件
     *
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
                sb.append("\n");
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
