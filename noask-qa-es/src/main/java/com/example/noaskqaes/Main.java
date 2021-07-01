package com.example.noaskqaes;

import com.example.noaskqaes.vo.dto.QaDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/25 17:18
 */
public class Main {
    static List<JudgeRep> QA_LIST = new ArrayList<>();
    static IQaService iQaService = new IQaService(QA_LIST);
    static String searchValue =
"社保交了咋个查";

    /*
84800001128005632	软件产品的增值税优惠？	软件产品的增值税减免
84800001128005632	软件产品的增值税优惠？	增值税减免软件产品
84800001128005632	软件产品的增值税优惠？	减免增值税软件产品
84800001128005632	软件产品的增值税优惠？	软件产品减免增值税
77017307961884672	如何申请个人所得税退税？	如何申请个人所得税退税
77014677054291968	如何缴纳个人租赁房屋又转租取得所得的个人所得税?	转租的房子个税怎么交
77014677054291968	如何缴纳个人租赁房屋又转租取得所得的个人所得税?	租的房子转租给别人怎么交个税
77013802963435520	如何缴纳个人出租住房的个人所得税？	我出租我自己住的房屋怎么交个税
75745276434841600	资源税申报期限是多久？	资源税的申报期限是怎么规定的
75745276434841600	资源税申报期限是多久？	资源税的申报期限的规定
75748176735240192	个人住房出租如何征收印花税？	个人出租住房怎么交印花税
75748176735240192	个人住房出租如何征收印花税？	个人出租住房印花税怎么交
75748176735240192	个人住房出租如何征收印花税？	我出租住房，税务局怎么收的印花税
75748176735240192	个人住房出租如何征收印花税？	我出租住房印花税税务局怎么收的
75747117786726400	申报环境保护税时需要提供什么资料？	我申报环保税需要交的资料有哪些
     */
    static {
        iQaService.initQa();
        iQaService.initQa1();
        iQaService.initQa2();
    }

    @Test
    public void testSearch() throws IOException {
//        ISearchService.dsl(searchValue, dsl -> dsl.size(3));
        List<QaDTO> qas = ISearchService.newSearch(searchValue, 3);
        for (QaDTO qa : qas) {
            System.out.println(qa.getDocName());
        }
        System.out.println();
    }
    @Test
    public void verify() {

    }

    @Test
    public void search() throws IOException {
        find();
    }

    @Test
    public void top() {
        iQaService.initQa();
        List<String> list = new ArrayList<>();
        for (JudgeRep res : QA_LIST) {
            String str = res.searchList;
            List<String> searchList = Arrays.asList(str.split(","));
            List<String> search = search(searchList, res.judgeId, res.judgeName);
            list.addAll(search);
        }
        String join = StringUtils.join(list, "\n");
        System.out.println(join);
    }

    public static List<String> search(List<String> searchList, String judgeId, String judgeName) {
        List<String> list = new ArrayList<>();
        for (String search : searchList) {
            String s = ISearchService.searchList(search, judgeId, judgeName);
            if (StringUtils.isNotEmpty(s)) {
                list.add(s);
            }
        }
        return list;
    }

    public void find() throws IOException {
        List<String> falseResult = new ArrayList<>();
        int size = 0;
        for (JudgeRep judgeExtend : QA_LIST) {
            String searchList = judgeExtend.searchList;
            String judgeId = judgeExtend.judgeId;
            String[] split = searchList.split(",");
            size += split.length;
            List<String> search = search(split, judgeId);
            falseResult.addAll(search);
        }
        String falseJoin = StringUtils.join(falseResult, ",");
        falseJoin = "".equals(falseJoin) ? "" : falseJoin + ",";
        BigDecimal divide = new BigDecimal(falseResult.size()).divide(new BigDecimal(size), 20, BigDecimal.ROUND_UP);
        System.out.println(String.format("问题一共：%s条, 匹配不上：%s条, 失败率：%s", size, falseResult.size(), divide));
        String wts = falseJoin.replaceAll("k:(.*?)——kName:(.*?)——v:(.*?)——result:(.*?),", "$1\t$2\t$3\t$4\n");
        List<String> wtList = Arrays.asList(wts.split("\n"));
//        List<String> baseSearch = IFileService.readBaseSearch();
//        List<String> upWtList = wtList.stream().filter(f -> !baseSearch.contains(f)).collect(Collectors.toList());
//        System.out.println("改动出现的问题集：\n" + StringUtils.join(upWtList, "\n"));
        System.out.println("总出现的问题集：\n" + wts);
        System.out.println();
    }

    private List<String> search(String[] searchValueList, String judgeId) throws IOException {
        List<String> list = new ArrayList<>();
        Map<String, String> map = IFileService.readQa();
        for (String search : searchValueList) {
            List<QaDTO> result = ISearchService.newSearch(search, 1);
            if (result.size() == 0) {
                continue;
            }
            List<String> docIdList = result.stream().map(QaDTO::getDocId).collect(Collectors.toList());
            if (docIdList.contains(judgeId)) {
                continue;
            }
            list.add(String.format("k:%s——kName:%s——v:%s——result:%s",judgeId, (map.get(judgeId) == null ? "" : map.get(judgeId)),
                    search, result.get(0).getDocName()));
        }
        return list;
//        return Arrays.stream(searchValueList).filter(f -> {
//            List<QaDTO> result = null;
//            try {
//                result = ISearchService.newSearch(f, 1);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (result == null || result.size() == 0) {
//                return false;
//            }
//            List<String> docIdList = result.stream().map(QaDTO::getDocId).collect(Collectors.toList());
//            return !docIdList.contains(judgeId);
//        }).map(m -> String.format("k:%s——kName:%s——v:%s——result:%s",
//                judgeId, (map.get(judgeId) == null ? "" : map.get(judgeId)), m)).collect(Collectors.toList());

    }
}
