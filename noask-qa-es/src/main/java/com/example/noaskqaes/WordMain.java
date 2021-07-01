package com.example.noaskqaes;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static com.hankcs.hanlp.corpus.tag.Nature.*;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/30 15:08
 */
public class WordMain {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class KvBo {
        /**
         * 快速注释
         **/
        private String k;
        private int v;
    }

    public static void sout(String name) {
        System.out.println("name=====================================================================================\n");
    }

    /**
     * 提取关键字
     */
    @Test
    public void word() {
        Map<String, String> map = IFileService.readQa();
        List<Term> segment = new ArrayList<>();
        for (String value : map.values()) {
            segment.addAll(HanLP.segment(value));
        }
        System.out.println("一共：" + segment.size() + "个词");
        segment = wordNoLoad(Arrays.asList(w, u, x, uv, nx, k, uj, ug, y, vg, p, z, dg, ad, c, gm, m, mq, ry, q,
                nrf, r, ul, ag, ud, h, ng, nr, v), segment);
        System.out.println("过滤后一共：" + segment.size() + "个词");
        System.out.println();
        sout("词性Sta");
        wordXing(segment);
        sout("词性End");

        sout("词频Sta");
        wordPing(segment);
        sout("词频End");

        System.out.println();
    }

    private static List<Term> wordLoad(List<Nature> natures, List<Term> terms) {
        return terms.stream().filter(f -> natures.contains(f.nature)).collect(Collectors.toList());
    }

    private static List<Term> wordNoLoad(List<Nature> natures, List<Term> terms) {
        return terms.stream().filter(f -> !natures.contains(f.nature)).collect(Collectors.toList());
    }

    private static void wordXing(List<Term> segment) {
        Map<Nature, List<Term>> natureMap = segment.stream().distinct().collect(Collectors.groupingBy(g -> g.nature));
        Map<String, String> hanLpLabelMap = IFileService.readHanLPLabel();
        natureMap.forEach((k, v) -> {
            List<String> words = v.stream().map(m -> m.word).distinct().collect(Collectors.toList());
            String join = StringUtils.join(words, ",");
            System.out.println(String.format("%s《%s》\t%s", k, (hanLpLabelMap.get(k.toString())), join));
        });
    }

    private static void wordPing(List<Term> segment) {
        Map<String, List<Term>> wordMap = segment.stream().collect(Collectors.groupingBy(g -> g.word));
        Map<Integer, List<KvBo>> sortedWordMap = wordMap.entrySet().stream().map(m -> new KvBo(m.getKey(), m.getValue().size()))
                .sorted(Comparator.comparing(KvBo::getV)).collect(Collectors.groupingBy(KvBo::getV));
        sortedWordMap.forEach((k, v) -> {
            List<String> vs = v.stream().map(KvBo::getK).collect(Collectors.toList());
            System.out.println(String.format("%s\t%s", k, StringUtils.join(vs, ",")));
        });
    }

}
