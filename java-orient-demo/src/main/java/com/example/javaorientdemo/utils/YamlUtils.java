package com.example.javaorientdemo.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.LinkedHashMap;

/**
 * yml配置读取工具类
 *
 * @author 周林
 * @version 1.0
 * @date 2020/12/1 11:56
 */
public class YamlUtils {

    public static String getProperty(String key) {
        Yaml yaml = new Yaml();
        InputStream in = YamlUtils.class.getResourceAsStream("/application.yml");
        LinkedHashMap<String, Object> sourceMap = yaml.load(in);
        return loadProps(sourceMap, key);
    }

    @SuppressWarnings({"unchecked"})
    private static String loadProps(LinkedHashMap<String, Object> sourceMap, String key) {
        String[] keys = key.split("[.]");
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) sourceMap.clone();
        int length = keys.length;
        String resultValue = null;
        for (int i = 0; i < length; i++) {
            Object value = map.get(keys[i]);
            if (i < length - 1) {
                map = ((LinkedHashMap<String, Object>) value);
            } else if (value == null) {
                throw new RuntimeException("key is not exists!");
            } else {
                resultValue = value.toString();
            }
        }
        return resultValue;
    }
}
