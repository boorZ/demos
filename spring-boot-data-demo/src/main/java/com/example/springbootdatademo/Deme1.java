package com.example.springbootdatademo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootdatademo.utils.C3P0Utils;
import com.example.springbootdatademo.utils.FileUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/5/26 16:40
 */
public class Deme1 {

    @Test
    public void safd() {
        String str = "湖南省财政厅湖南省交通运输厅关于印发《湖南省交通运输事业发展专项资金管理暂行办法》的通知";
        String s = nameRegex("[\\u4e00-\\u9fa5]+", str);
        s = nameRegex("(?<=关于).*", s);
        System.out.println(s);
    }
    public String nameTo(String name) {
        String str = nameRegex("[\\u4e00-\\u9fa5]+", name);
        str = nameRegex("(?<=关于).*", str);
        return StringUtils.isNotEmpty(str) ? str : name;
    }
    @Test
    public void test() throws PropertyVetoException, SQLException {
//        getNowLaw();
//        String key = "law_name";
//        String key = "writ_no";
        List<LawBo> law6 = lawToJava("62802.json");
        List<LawBo> law9 = lawToJava("94870.json");
        System.out.println(law6.size() + ", " + law9.size());

//        law6 = law6.stream().filter(f -> f.getId().equals(342498545112192604L)).collect(Collectors.toList());
//        law9 = law9.stream().filter(f -> f.getId().equals(188391002183045120L)).collect(Collectors.toList());
        List<String> law9IdName = law9.stream().map(m -> nameTo(m.getName())).collect(Collectors.toList());
        List<Long> law6IdNameRule = law6.stream().filter(f -> law9IdName.contains(nameTo(f.getName()))).map(m -> m.getId()).collect(Collectors.toList());
        String join = StringUtils.join(law6IdNameRule, ",");

        System.out.println();


//        List<String> names = law6.stream().map(m -> m.getName() + m.getNo()).collect(Collectors.toList());
        List<String> names = law6.stream().map(LawBo::getName).collect(Collectors.toList());
        List<String> nos = law6.stream().map(LawBo::getNo).collect(Collectors.toList());

//        List<String> names1 = law9.stream().map(m -> m.getName() + m.getNo()).collect(Collectors.toList());
        List<String> names1 = law9.stream().map(LawBo::getName).collect(Collectors.toList());
        List<String> nos1 = law9.stream().map(LawBo::getNo).collect(Collectors.toList());

//        List<String> collect = names.stream().filter(names1::contains).collect(Collectors.toList());
        List<String> collect1 = names.stream().filter(names1::contains).collect(Collectors.toList());
        List<String> collect2 = nos.stream().filter(nos1::contains).collect(Collectors.toList());

//        System.out.println(collect.size());
//        System.out.println(StringUtils.join(collect, ","));
        System.out.println(StringUtils.join(collect1, ","));
        System.out.println(StringUtils.join(collect2, ","));
        System.out.println();
        /*
        导入法规62802条
            与库重复数据：
                名称- 805 条
                文号- 53 条
                名称;文号- 11 条
            文号缺失： 70 条（内容为“无”）
            发布时间缺失： 622 条
            正文内容为空： 0 条
         */
        // 名称存在 原 62802
    }

    public static List<String> baseRegex(String regex, String input) {
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(input);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group(0);
            result.add(group);
        }
        return result;
    }
    public static String nameRegex(String regex, String input) {
        List<String> strings = baseRegex(regex, input);
        return StringUtils.join(strings, "");
    }

    private List<String> lawOne(String fileName, String key) {
        JSONArray law = law(fileName);
        return law.stream().map(m -> JSONObject.parseObject(m.toString()).getString(key)).collect(Collectors.toList());
    }

    private List<LawBo> lawToJava(String fileName) {
        JSONArray law = law(fileName);
        return law.stream().map(m -> JSONObject.parseObject(m.toString()).toJavaObject(LawBo.class)).collect(Collectors.toList());
    }

    private JSONArray law(String fileName) {
        String read = FileUtils.read("jsons/" + fileName);
        JSONObject zbJson = JSONObject.parseObject(read);
        return zbJson.getJSONArray("data");
    }

    @Data
    static class LawBo {
        private Long id;
        private String name;
        private String no;
        private String tm;
    }

    private void getNowLaw() throws PropertyVetoException, SQLException {
        String sql = "SELECT  law_name, writ_no, dispatch_time FROM `t_doc_law` limit 1;";
        ResultSet rs = C3P0Utils.getConnection(sql);
        while (rs.next()) {
            String law_name = rs.getString("law_name");
            System.out.println();
        }
        rs.close();
        C3P0Utils.closePs();
    }
}
