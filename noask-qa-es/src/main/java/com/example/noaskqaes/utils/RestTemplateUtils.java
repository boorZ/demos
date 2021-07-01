package com.example.noaskqaes.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

/**
 * RestTemplate请求工具类
 *
 * @author 周林
 * @version 1.0
 * @date 2020/11/30 14:57
 */
@Slf4j
public class RestTemplateUtils {

    /**
     * 封装的get请求，暂时只支持map传参，并且value只支持基本类型和String
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String getForObject(String url, Map<String, String> paramMap) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        StringBuilder stringBuffer = new StringBuilder(url);
        Iterator<Map.Entry<String, String>> iterator = paramMap.entrySet()
                .iterator();
        if (iterator.hasNext()) {
            stringBuffer.append("?");
            Object element;
            while (iterator.hasNext()) {
                element = iterator.next();
                Map.Entry<String, Object> entry = (Map.Entry) element;
                //过滤value为null，value为null时进行拼接字符串会变成 "null"字符串
                if (entry.getValue() != null) {
                    stringBuffer.append(element).append("&");
                }
                url = stringBuffer.substring(0, stringBuffer.length() - 1);
            }
        }
//        log.info("url请求:" + url);
        return restTemplate.getForObject(url, String.class);
    }

    public static String postForObject(String url, Map<Object, Object> paramMap) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, paramMap, String.class);
    }

    public static <T> T postForObject(String url, Map<Object, Object> paramMap, Class<T> responseType) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, paramMap, responseType);
    }
}
