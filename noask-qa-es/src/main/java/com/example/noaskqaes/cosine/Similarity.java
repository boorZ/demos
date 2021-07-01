package com.example.noaskqaes.cosine;

import java.util.Arrays;
import java.util.List;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/28 10:27
 */
public class Similarity {
    public static final String content1 = "今天小小和爸爸一起去摘草莓，小小说今天的草莓特别的酸，而且特别的小，关键价格还贵";

    public static final String content2 = "今天小小和妈妈一起去草原里采草莓，今天的草莓味道特别好，而且价格还挺实惠的";


    public static void main(String[] args) {
//        similarity("哪些情形不需要办理个税汇算", Arrays.asList(
        similarity("哪些情形不需要办理个人所得税汇算清缴", Arrays.asList(
                "哪些人不需要办理年度汇算？",
                "需要办理个人所得税年度汇算清缴的情形？",
                "哪些情形应当办理综合所得年度汇算补税？",
                "取得综合所得需要办理汇算清缴的情形包括？",
                "无需办理个人所得税年度汇算清缴的情形？"));
    }

    public static void similarity(String sta, String end) {
        double score = CosineSimilarity.getSimilarity(sta, end);
        System.out.println("相似度：" + score);
    }

    public static void similarity(String sta, List<String> ends) {
        for (String end : ends) {
            similarity(sta, end);
            System.out.println();
        }
    }
}
