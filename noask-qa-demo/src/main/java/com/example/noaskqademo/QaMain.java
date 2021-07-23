package com.example.noaskqademo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.noaskqademo.utils.FileUtils;
import com.example.noaskqademo.vo.QaBO;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/25 10:12
 */
public class QaMain {

    @Test
    public void dataType() {
        List<QaBO> qaBos = readFileJava("jsons/qa.json", QaBO.class);
        List<String> docNameList = qaBos.stream().map(QaBO::getDocName).collect(Collectors.toList());
        List<String> 个人所得税 = containsDocName("个人所得税", docNameList);
        List<String> 个税 = containsDocName("个税", docNameList);
        System.out.println();
    }

    public static List<String> containsDocName(String includeName, List<String> docNameList) {
        return docNameList.stream().filter(f -> f.contains(includeName)).collect(Collectors.toList());
    }

    public static <T> List<T> readFileJava(String path, Class<T> clazz) {
        String data = FileUtils.read(path);
        JSONArray zbJson = JSONArray.parseArray(data);
        return zbJson.stream().map(m -> JSONObject.parseObject(m.toString()).toJavaObject(clazz)).
                collect(Collectors.toList());
    }
}
