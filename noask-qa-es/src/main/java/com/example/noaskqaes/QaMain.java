package com.example.noaskqaes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.noaskqaes.service.Search;
import com.example.noaskqaes.utils.FileUtils;
import com.example.noaskqaes.utils.RestTemplateUtils;
import com.example.noaskqaes.vo.dto.QaDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/22 18:07
 */
@Slf4j
public class QaMain extends QaData{

    {
        initQa();
//        iQaService.initQa1();
//        iQaService.initQa2();
//        iQaService.initQa3();
    }

    @Test
    public void asdf() throws IOException {
        String s = baseTest("离婚办理房屋产权过户是否征收个人所得税", "77014221652492288");
        System.out.println(s);
    }

    @Test
    public void testConn() throws IOException {
//        List<String> collect = QA_LIST.stream().map(m -> m.searchList).collect(Collectors.toList());
        List<String> list = new ArrayList<>();
        for (JudgeRep judgeRep : QA_LIST) {
            String judgeId = judgeRep.judgeId;
            String searchList = judgeRep.searchList;
            String[] split = searchList.split(",");
            for (String s : split) {
                String s1 = baseTest(s, judgeId);
                if (s1 == null) continue;
                list.add(String.format("%s\t%s\t%s", s, judgeRep.judgeName, s1));
            }
        }
        System.out.println("====================================");
        System.out.println(StringUtils.join(list, "\n"));
    }

    private static String baseTest(String searchValue, String judgeId) throws IOException {
//        Map<String, String> search = search(searchValue);
        List<QaDTO> qas = Search.newSearch(searchValue, 50);
        if (qas.size() < 3) {
            return null;
        }
        List<String> data = new ArrayList<>();
        data.add(searchValue);
        List<String> docNames = qas.stream().map(QaDTO::getDocName).distinct().collect(Collectors.toList());
        data.addAll(docNames);
        boolean contains = qas.stream().map(QaDTO::getDocId).collect(Collectors.toList()).contains(judgeId);

        Map<Object, Object> paramMap = new HashMap<>();
        paramMap.put("contents", data);
        String result = RestTemplateUtils.postForObject("http://10.8.0.9:50015/match", paramMap);
        return String.format("%s\t%s\t%s", result.replaceAll("[{}\"|reank:]", ""), docNames, contains);
    }

    @Test
    public void test() throws InterruptedException {
//        List<String> list = new ArrayList<>();
        String read = FileUtils.read("szKeyWord/newQa");
        List<String> regex = regex("- intent.*?(?=- intent)", read);
        for (String s : regex) {
            String s1 = regexOne("(?<=- intent: ).*(?=examples)", s);
            String s2 = regexOne("(?<=examples:).*", s);
            List<String> search = search(Arrays.asList(s2.split(",")), "", s1);
//            list.addAll(search);
            generateFile("nameList.txt", StringUtils.join(search, "\n"));
            Thread.sleep(2000);
        }
//        String join = StringUtils.join(list, "\n");
//        System.out.println(join);
//        System.out.println(list.size());
        System.out.println();
    }

    protected static String GENERATE_PATH = "D:" + File.separator + "qa" + File.separator;

    /**
     * 生成文件
     *
     * @param fileName
     * @param content
     * @throws IOException
     */
    public static void generateFile(String fileName, String content) {
        // 文件目录
        Path path = Paths.get(GENERATE_PATH + fileName);
        //追加写模式
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String regexOne(String regex, String text) {
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(text);
        boolean b = matcher.find();
        return b ? matcher.group(0) : "";
    }

    private static List<String> regex(String regex, String text) {
        List<String> list = new ArrayList<>();
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(text);
        while (matcher.find()) {
            list.add(matcher.group(0));
        }
        return list;
    }

    public void main(String[] args) {
//        String str = "", judgeId = "", judgeName = "";
//        List<String> searchList;
        List<String> list = new ArrayList<>();
        for (JudgeRep res : super.QA_LIST) {
            String str = res.searchList;
            List<String> searchList = Arrays.asList(str.split(","));
            List<String> search = search(searchList, res.judgeId, res.judgeName);
            list.addAll(search);
        }
        String join = StringUtils.join(list, "\n");
        System.out.println(join);

        System.out.println(list.size());
    }

    public static List<String> search(List<String> searchList, String judgeId, String judgeName) {
        List<String> list = new ArrayList<>();
        for (String search : searchList) {
            String s = searchList(search, judgeId, judgeName);
            if (StringUtils.isNotEmpty(s)) {
                list.add(s);
            }
        }
        return list;
    }

    public static String searchList(String search, String judgeId, String judgeName) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("search", search);
        String forObject = RestTemplateUtils.getForObject("https://sw.noask-ai.com/portal/qa-new/hotline/es/list", paramMap);
        JSONObject jsonObject = JSONObject.parseObject(forObject);
        JSONArray results = jsonObject.getJSONArray("result");
        if (results == null || results.size() == 0) {
//            System.out.println(String.format("search:%s，无数据", search));
            return "";
        }
        Object result = results.get(0);
        JSONObject resultJson = JSONObject.parseObject(result.toString());
        String docId = resultJson.getString("docId");
        boolean isDocId = docId.equals(judgeId);
//        System.out.println(String.format("search:%s, docId:%s", search, isDocId));

        int size = results.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            String s = ((size - 1) < i ? "" : docName(results.get(i)));
            sb.append("\t").append(s);
        }
        return String.format("%s%s\t%s\t%s\t%s", search, sb.toString(), judgeName, (isDocId ? 0 : 1), (isDocId ? 1 : 0));
    }

    public static Map<String, String> search(String search) {
        Map<String, String> list = new HashMap<>();

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("search", search);
        String forObject = RestTemplateUtils.getForObject("https://sw.noask-ai.com/portal/qa-new/hotline/es/list", paramMap);
        JSONObject jsonObject = JSONObject.parseObject(forObject);
        JSONArray results = jsonObject.getJSONArray("result");
        if (results == null || results.size() == 0) {
//            System.out.println(String.format("search:%s，无数据", search));
            return list;
        }
        for (Object result : results) {
            JSONObject resultJson = JSONObject.parseObject(result.toString());
            String docId = resultJson.getString("docId");
            String docName = resultJson.getString("docName");
            list.put(docId, docName);
        }
        return list;
    }

    public static void search(String search, String judgeId, String judgeName) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("search", search);
        String forObject = RestTemplateUtils.getForObject("https://sw.noask-ai.com/portal/qa-new/hotline/es/list", paramMap);
        JSONObject jsonObject = JSONObject.parseObject(forObject);
        JSONObject result = jsonObject.getJSONObject("result");
        if (result == null) {
            log.info("\nsearch:{}，无数据", search);
            return;
        }
        log.info("\nsearch:{}, docId:{}", search, verify("docId", judgeId, result));
    }

    public static String docName(Object result) {
        JSONObject resultJson = JSONObject.parseObject(result.toString());
        return resultJson.getString("docName");
    }

    public static boolean verify(String key, String name, JSONObject result) {
        return result.getString(key).equals(name);
    }
}
