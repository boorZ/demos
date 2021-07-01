package com.example.noaskqaes;

import org.junit.Test;

import java.util.Collection;
import java.util.Map;

/**
 * 分析问答
 *
 * @author 周林
 * @version 1.0
 * @date 2021/7/1 17:43
 */
public class AnalysisQAService {
    @Test
    public void tests() {
        Map<String, String> map = IFileService.readQa();
        Collection<String> values = map.values();
//        values.stream().filter(f -> f.contains("个人"))
        System.out.println();
    }
}
