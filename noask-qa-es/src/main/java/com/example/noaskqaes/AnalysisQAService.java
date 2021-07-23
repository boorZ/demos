package com.example.noaskqaes;

import com.example.noaskqaes.vo.dto.QaDTO;
import com.hankcs.hanlp.HanLP;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分析问答
 *
 * @author 周林
 * @version 1.0
 * @date 2021/7/1 17:43
 */
public class AnalysisQAService {
    protected List<QaDTO> qaData;
    protected List<String> szs;

//    {
//        szs = Arrays.asList(("企业所得税,个人所得税,增值税,消费税,车辆购置税,房产税,城镇土地使用税,土地增值税,耕地占用税,资源税,契税,印花税,车船税,烟叶税,环境保护税," +
//                "城市维护建设税,船舶吨税,关税,营业税,社会保险费,工会经费,教育费附加,地方教育费附加,非税收入").split(","));
//        qaData = new ArrayList<>();
//        Map<String, String> map = IFileService.readQa();
//        map.forEach((k, v) -> qaData.add(new QaDTO(k, v)));
//    }

    @Test
    public void getWord() {
        int size = 100;
        for (QaDTO qa : qaData.subList(0, 10)) {
            String docName = qa.getDocName();
            System.out.println(docName);
//            List<String> strings = HanLP.extractKeyword(docName, 100);
//            List<String> strings2 = HanLP.extractPhrase(docName, 100);
            System.out.println("=提取词语: " + HanLP.extractWords(docName, size));
            System.out.println("=自动摘要: " + HanLP.extractSummary(docName, size));
//            System.out.println(String.format("%s\t%s\t%s", docName, strings, strings2));
            System.out.println();
        }
    }

    @Test
    public void sz() {
        String str = "非直系亲属继承是否缴纳土地增值税？";
        for (String sz : szs) {
            if (str.contains(sz)) {
                System.out.println(sz);
            }
        }
    }

    @Test
    public void tests() {
        List<QaDTO> filters = new ArrayList<>();
        for (String sz : szs) {
            List<QaDTO> filter = containName(sz);
            System.out.println(sz + "\t" + filter.size());
            filters.addAll(filter);
        }
        Map<String, List<QaDTO>> filtersGroup = filters.stream().collect(Collectors.groupingBy(QaDTO::getDocId));
        List<QaDTO> repetition = filtersGroup.values().stream().filter(f -> f.size() > 1).map(m -> m.get(0)).collect(Collectors.toList());


        List<QaDTO> qaDataExtends = new ArrayList<>(qaData);
        qaDataExtends.removeAll(filters);
        List<QaDTO> 个税 = qaDataExtends.stream().filter(f -> f.getDocName().contains("个税")).collect(Collectors.toList());
        System.out.println();
    }

    public List<QaDTO> containName(String str) {
        return qaData.stream().filter(f -> f.getDocName().contains(str)).collect(Collectors.toList());
    }
}
