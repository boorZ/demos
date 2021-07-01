package com.example.noaskqaes;

import com.example.noaskqaes.utils.FileUtils;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import org.junit.Test;
import org.ujmp.core.Matrix;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/29 10:36
 */
public class IFileService {
    protected final static String DATA_PATH = "datas/";
    protected final static String WORDS_PATH = "words/";
    protected final static String ANALYSIS_PATH = "analysis/";

    public static String readSynonym() {
        return FileUtils.read(ANALYSIS_PATH + "synonym.txt");
    }
    public static String readMappingSynonym() {
        return FileUtils.read(ANALYSIS_PATH + "mappingSynnym.txt");
    }
    public static String readCoreWord() {
        return FileUtils.read(ANALYSIS_PATH + "coreWord.txt");
    }
    public static List<String> readInterval() {
        String read = FileUtils.read(ANALYSIS_PATH + "interval.txt");
        if ("".equals(read)) {
            return new ArrayList<>();
        }
        return Arrays.asList(read.split("\n"));
    }

    public static Map<String, String> readQa() {
        return FileUtils.readMap(DATA_PATH + "qa.json", "docId", "docName");
    }
    public static List<String> readBaseSearch() {
        String read = FileUtils.read(DATA_PATH + "baseSearch.txt");
//        String[] datas = read.split("\n");
//        return Arrays.stream(datas).map(m -> m.split("\t")[2]).collect(Collectors.toList());
        return Arrays.asList(read.split("\n"));
    }

    public static Map<String, String> readHanLPLabel() {
        String read = FileUtils.read(WORDS_PATH + "HanLP词性标注集.txt");
        Map<String, String> map = new HashMap<>();
        String[] split = read.split("\n");
        for (String s : split) {
            String[] split1 = s.split(":");
            map.put(split1[0], split1[1]);
        }
        return map;
    }
    public static Map<String, Integer> readWordFrequency(int numberSize) {
        Map<String, Integer> map = new HashMap<>();
        String read = FileUtils.read(WORDS_PATH + "word_frequency.txt");
        String[] datas = read.split("\n");
        for (String data : datas) {
            String[] split = data.split("\t");
            int i = Integer.parseInt(split[0]);
            if (numberSize < Integer.parseInt(split[0])) {
                continue;
            }
            String[] split1 = split[1].split(",");
            for (String s : split1) {
                map.put(s, i);
            }
        }
        return map;
    }
    public static Map<Integer, Integer> readWordFrequencyScore() {
        Map<Integer, Integer> map = new HashMap<>();
        String read = FileUtils.read(WORDS_PATH + "word_frequency_score.txt");
        String[] split = read.split("\n");
        for (String s : split) {
            String[] split1 = s.split(" ");
            String[] split2 = split1[0].split("-");
            int score = Integer.parseInt(split1[1]);
            if (split2.length == 2) {
                for (int i = Integer.parseInt(split2[1]); i >=  Integer.parseInt(split2[0]); i--) {
                    map.put(i, score);
                }
                continue;
            }
            map.put(Integer.parseInt(split2[0]), score);
        }
        return map;
    }
    public static Map<String, Integer> readWordMemory() {
        Map<String, Integer> map = new HashMap<>();
        String read = FileUtils.read(WORDS_PATH + "word_memory.txt");
        String[] split = read.split("\n");
        for (String s : split) {
            String[] s1 = s.split(" ");
            map.put(s1[0], Integer.parseInt(s1[2]));
        }
        return map;
    }
    @Test
    public void score() {
        long staTime = System.currentTimeMillis();
        String searchValue = "疫情期间小规模纳税人怎样申报增值税";
        List<Term> termList = HanLP.segment(searchValue);
        // 词性
        Map<String, Integer> wordMemoryMap = readWordMemory();
        // 前100词频 词
        Map<String, Integer> wordFrequencyMap = readWordFrequency(100);
        // 词频权重
        Map<Integer, Integer> wordFrequencyScoreMap = readWordFrequencyScore();
        for (Term term : termList) {
            // 求词性分
            Integer memoryScore = wordMemoryMap.get(term.nature.toString());
            memoryScore = memoryScore == null ? 0 : memoryScore;
            // 求词频分
            Integer frequency = wordFrequencyMap.get(term.word);
            Integer frequencyScore = wordFrequencyScoreMap.get(frequency);
            frequencyScore = frequencyScore == null ? 0 : frequencyScore;

            System.out.println(String.format("词：%s\t词性分：%s\t词频分：%s", term, memoryScore, frequencyScore));
        }
        System.out.println("执行时间：" + (System.currentTimeMillis() - staTime));
    }
    public static void main(String[] args) {
//        Matrix matrix = Matrix.Factory.zeros(5, 5);
//        matrix.setAsInt(1, 0, 0);
//        matrix.setAsInt(2, 0, 1);
//        matrix.setAsInt(3, 1, 0);
//        matrix.setAsInt(4, 2, 1);
//        matrix.showGUI();

//        Integer integer = readWordFrequencyScore(100);
//        System.out.println(integer);
        Map<String, Integer> wordMemoryMap = readWordMemory();

        List<Integer> scoreList = wordMemoryMap.values().stream().distinct().collect(Collectors.toList());
        int size = wordMemoryMap.size();
        Matrix matrix = Matrix.Factory.zeros(size, scoreList.size() + 1);

        AtomicInteger i = new AtomicInteger();
        wordMemoryMap.forEach((k, v) -> {
            matrix.setAsString(k, i.get(), 0);
            matrix.setAsInt(v, i.get(), 1);
            i.getAndIncrement();
        });
        System.out.println(matrix);
    }
}
