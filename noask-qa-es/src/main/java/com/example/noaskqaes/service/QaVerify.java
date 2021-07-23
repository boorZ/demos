package com.example.noaskqaes.service;

import com.example.noaskqaes.QaData;
import com.example.noaskqaes.JudgeRep;
import com.example.noaskqaes.utils.BaseVariableUtils;
import com.example.noaskqaes.utils.RestTemplateUtils;
import com.example.noaskqaes.vo.dto.QaDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 为胥博测试模型用
 *
 * @author 周林
 * @version 1.0
 * @date 2021/7/2 14:40
 */
public class QaVerify extends QaData{
    protected String nlpPortRegex = ".*\\[(.*?)].*";
    protected String nlpPortUrl = "http://10.8.0.9:50015/match";

    @Test
    public void verifyTest() {
        long staTime = System.currentTimeMillis();
        initQa();
        List<String> verify = verify(QA_LIST, 5);
        System.out.println(StringUtils.join(verify, "\n"));
        BaseVariableUtils.statsTime("总共", staTime);
    }

    /**
     * NLP接口
     *
     * @param topValues   top值
     * @param searchValue 搜索值
     * @return
     */
    protected List<Integer> nlpPort(String searchValue, String[] topValues) {
        long staTime = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        Collections.addAll(list, topValues);
        list.add(0, searchValue);

        Map<Object, Object> paramMap = new HashMap<>(16);
        paramMap.put("contents", list);
        String response = RestTemplateUtils.postForObject(nlpPortUrl, paramMap);
        String resultRegex = response.replaceAll(nlpPortRegex, "$1");
        List<Integer> result = Arrays.stream(resultRegex.split(", ")).map(Integer::parseInt).collect(Collectors.toList());
        BaseVariableUtils.statsTime("NLP接口", staTime);
        return result;
    }

    public String verify(String searchValue, String verifyDocId, String verifyDocName, Integer topSize) {
        long staTime = System.currentTimeMillis();
        List<QaDTO> esTopValues = null;
        try {
            esTopValues = Search.newSearch(searchValue, topSize);
        } catch (IOException ignored) {
        }
        if (esTopValues == null || esTopValues.size() < 2) return "";
        String[] docNames = esTopValues.stream().map(QaDTO::getDocName).toArray(String[]::new);
        List<String> docIds = esTopValues.stream().map(QaDTO::getDocId).collect(Collectors.toList());
        List<Integer> nlpPort = this.nlpPort(searchValue, docNames);
        // 搜索值  nlp返回值  ES返回值   答案是否在ES返回值中
        String format = String.format("%s\t%s\t%s\t%s", searchValue, verifyDocName, nlpPort, docIds.contains(verifyDocId));
        BaseVariableUtils.statsTime("单次校验", staTime);
        System.out.println();
        return format;
    }

    public List<String> verify(List<JudgeRep> qaList, Integer topSize) {
        List<String> list = new ArrayList<>();
        for (JudgeRep judgeRep : qaList) {
            String searchList = judgeRep.searchList;
            String[] searchValues = searchList.split(",");
            List<String> result = Arrays.stream(searchValues).map(m ->
                    verify(m, judgeRep.judgeId, judgeRep.judgeName, topSize)).collect(Collectors.toList());
            list.addAll(result);
        }
        return list;
    }
}
