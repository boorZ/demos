package com.example.noasksldemo.sz;

import com.alibaba.fastjson.JSONArray;
import com.example.noasksldemo.utils.OrientUtils;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.OEdge;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/17 17:39
 */
@Slf4j
public class SzMain {
    public static String database = "sl-demo";

    public static String szKeyword = "sz_keyword_v";
    public static String szV = "sz_v";
    public static String szE = "sz_e";

    @Test
    public void searchSl() {
        String searchValue = "综合所得个人所得税的税率是多少？税率表？";
        searchValue = "非居民个人工资、薪金所得按多少税率缴税？";
        searchValue = "个人所得税的税率是多少？";
//        searchValue = "我今年工资？";
        
        System.out.println();
    }

    @Test
    public void initOrientSz() {
        OrientUtils.execute(db -> {
            initVE(db);
            initData(db);
        }, database);
        System.out.println();
    }

    private void initVE(ODatabaseSession db) {
        OClass szVo = db.getClass(szV);
        OClass szEo = db.getClass(szE);
        OClass szVoKeyword = db.getClass(szKeyword);
        if (szVo == null) {
            szVo = db.createVertexClass(szV);
            szVo.createProperty("name", OType.STRING);
            szVo.createProperty("remark", OType.STRING);
//            szVo.createIndex("name", OClass.INDEX_TYPE.NOTUNIQUE, "name");
        }
        if (szEo == null) {
            szEo = db.createEdgeClass(szE);
            szEo.createProperty("name", OType.STRING);
            szEo.createProperty("remark", OType.STRING);
        }
        if (szVoKeyword == null) {
            szVoKeyword = db.createVertexClass(szKeyword);
            szVoKeyword.createProperty("name", OType.STRING);
            szVoKeyword.createProperty("remark", OType.STRING);
            szVoKeyword.createProperty("weight", OType.INTEGER).setMin("0").setMax("10").setDefaultValue("0").isNotNull();
//            szVoKeyword.createIndex("name", OClass.INDEX_TYPE.NOTUNIQUE, "name");
        }
    }

    public void initData(ODatabaseSession db) {
        OVertex 个人所得税 = createV(db, "个人所得税");
        initData(db, 个人所得税, Arrays.asList("个人所得税", "个税", "个人所得税法"));
        OVertex time = createV(db, "2019.1.1-至今", "2019.1.1", null);
        OVertex 居民个人 = createV(db, "居民个人");
        OVertex 非居民个人 = createV(db, "非居民个人");
        OVertex 取得综合所得 = createV(db, "取得综合所得",
                "不超过36000元的部分\t3%\t0\n" +
                        "超过36000元至144000元的部分\t10%\t2520\n" +
                        "超过144000元至300000元的部分\t20%\t16920\n" +
                        "超过300000元至420000元的部分\t25%\t31920\n" +
                        "超过420000元至660000元的部分\t30%\t52920\n" +
                        "超过660000元至960000元的部分\t35%\t85920\n" +
                        "超过960000元的部分\t45%\t181920\n");
        initData(db, 取得综合所得, Arrays.asList("取得综合所得", "综合所得"));
        OVertex 工资薪金个人所得税预缴 = createV(db, "工资、薪金个人所得税预缴",
                "不超过36000元的部分\t3%\t0\n" +
                        "超过36000元至144000元的部分\t10%\t2520\n" +
                        "超过144000元至300000元的部分\t20%\t16920\n" +
                        "超过300000元至420000元的部分\t25%\t31920\n" +
                        "超过420000元至660000元的部分\t30%\t52920\n" +
                        "超过660000元至960000元的部分\t35%\t85920\n" +
                        "超过960000元的部分\t45%\t181920\n");
        initData(db, 工资薪金个人所得税预缴, Arrays.asList("工资薪金个人所得税预缴", "工资薪金", "预缴"));
        OVertex 劳务报酬个人所得税预缴 = createV(db, "劳务报酬个人所得税预缴",
                "不超过20000元的\t20%\t \n" +
                        "超过20000元至50000元的部分\t30%\t2000\n" +
                        "超过50000元的部分\t40%\t7000\n");
        initData(db, 劳务报酬个人所得税预缴, Arrays.asList("劳务报酬个人所得税预缴", "劳务报酬", "预缴"));
        OVertex 稿酬特许权使用费个人所得税预缴 = createV(db, "稿酬、特许权使用费个人所得税预缴", " \t20%\t ");
        initData(db, 稿酬特许权使用费个人所得税预缴, Arrays.asList("稿酬特许权使用费个人所得税预缴", "特许权", "稿酬", "使用费", "预缴"));
        OVertex 经营所得 = createV(db, "经营所得",
                "不超过30000元的部分\t5%\t0\n" +
                        "超过30000元至90000元的部分\t10%\t1500\n" +
                        "超过90000元至300000元的部分\t20%\t10500\n" +
                        "超过300000元至500000元的部分\t30%\t40500\n" +
                        "超过500000元的部分\t35%\t65500\n");
        initData(db, 经营所得, Arrays.asList("经营所得", "经营"));
        OVertex 财产租赁所得 = createV(db, "财产租赁所得", " \t20%\t ");
        initData(db, 财产租赁所得, Arrays.asList("财产租赁所得", "财产租赁", "财产"));
        OVertex 财产转让所得 = createV(db, "财产转让所得", " \t20%\t ");
        initData(db, 财产转让所得, Arrays.asList("财产转让所得", "财产转让", "财产"));
        OVertex 利息股息红利所得和偶然所得 = createV(db, "利息、股息、红利所得和偶然所得", " \t20%\t ");
        initData(db, 利息股息红利所得和偶然所得, Arrays.asList("利息股息红利所得和偶然所得", "利息股息", "利息红利", "股息红利", "红利所得", "偶然所得"));
        OVertex 工资薪金劳务报酬稿酬特许权使用费 = createV(db, "工资、薪金,劳务报酬,稿酬,特许权使用费",
                "不超过3000元的部分\t3%\t0\n" +
                        "超过3000元至12000元的部分\t10%\t210\n" +
                        "超过12000元至25000元的部分\t20%\t1410\n" +
                        "超过25000元至35000元的部分\t25%\t2660\n" +
                        "超过35000元至55000元的部分\t30%\t4410\n" +
                        "超过55000元至80000元的部分\t35%\t7160\n" +
                        "超过80000元的部分\t45%\t15160\n");
        initData(db, 工资薪金劳务报酬稿酬特许权使用费, Arrays.asList("工资薪金劳务报酬稿酬特许权使用费", "工资薪金", "劳务报酬", "稿酬", "特许权", "使用费"));
//        OVertex 税率 = createV(db, "税率");
        createE(db, 个人所得税, time);
        createE(db, time, Arrays.asList(居民个人, 非居民个人));

        createE(db, 居民个人, Arrays.asList(取得综合所得, 工资薪金个人所得税预缴, 劳务报酬个人所得税预缴,
                稿酬特许权使用费个人所得税预缴, 经营所得, 财产租赁所得, 财产转让所得, 利息股息红利所得和偶然所得));
        createE(db, 非居民个人, Arrays.asList(工资薪金劳务报酬稿酬特许权使用费, 经营所得, 财产租赁所得, 财产转让所得,
                利息股息红利所得和偶然所得));
//        createE(db, Arrays.asList(取得综合所得,工资薪金个人所得税预缴,劳务报酬个人所得税预缴,稿酬特许权使用费个人所得税预缴,
//                经营所得,财产租赁所得,财产转让所得,利息股息红利所得和偶然所得,工资薪金劳务报酬稿酬特许权使用费), 税率);

//        JSONArray objects = new JSONArray();

    }

    public void initData(ODatabaseSession db, OVertex topName, List<String> list) {
        for (String str : list) {
            OVertex strV = createKeyword(db, str);
            createE(db, strV, topName);
        }
    }

    public void initData(ODatabaseSession db, String topName, List<String> list) {
        OVertex topNameV = createV(db, topName);
        for (String str : list) {
            OVertex strV = createKeyword(db, str);
            createE(db, strV, topNameV);
        }
    }


    private static void createE(ODatabaseSession db, OVertex sta, OVertex end) {
        OEdge result = db.newEdge(sta, end, szE);
        result.save();
    }

    private static void createE(ODatabaseSession db, OVertex sta, List<OVertex> ends) {
        for (OVertex end : ends) {
            OEdge result = db.newEdge(sta, end, szE);
            result.save();
        }
    }

    private static void createE(ODatabaseSession db, List<OVertex> stas, OVertex end) {
        for (OVertex sta : stas) {
            OEdge result = db.newEdge(sta, end, szE);
            result.save();
        }
    }

    private static void createE(ODatabaseSession db, OVertex sta, OVertex end, JSONArray property) {
        OEdge result = db.newEdge(sta, end, szE);
        result.setProperty("sl", property);
        result.save();
    }

    private static void createE(ODatabaseSession db, OVertex sta, OVertex end, Map<String, Object> property) {
        OEdge result = db.newEdge(sta, end, szE);
        property.forEach(result::setProperty);
        result.save();
    }

    private static void createE(ODatabaseSession db, OVertex sta, String end) {
        OVertex endV = createV(db, end);
        OEdge result = db.newEdge(sta, endV, szE);
        result.save();
    }

    private static void createE(ODatabaseSession db, String sta, OVertex end) {
        OVertex staV = createV(db, sta);
        OEdge result = db.newEdge(staV, end, szE);
        result.save();
    }

    private static OVertex createV(ODatabaseSession db, String name) {
        OVertex result = db.newVertex(szV);
        result.setProperty("name", name);
        result.save();
        return result;
    }

    private static OVertex createV(ODatabaseSession db, String name, String sl) {
        OVertex result = db.newVertex(szV);
        result.setProperty("name", name);
        result.setProperty("sl", sl(sl));
        result.save();
        return result;
    }

    private static OVertex createV(ODatabaseSession db, String name, String staTime, String endTime) {
        OVertex result = db.newVertex(szV);
        result.setProperty("name", name);
        result.setProperty("staTime", staTime);
        result.setProperty("endTime", endTime);
        result.save();
        return result;
    }

    private static OVertex createKeyword(ODatabaseSession db, String name) {
        OVertex result = db.newVertex(szKeyword);
        result.setProperty("name", name);
        result.save();
        return result;
    }

    private static OVertex createKeyword(ODatabaseSession db, String name, Map<String, Object> property) {
        OVertex result = db.newVertex(szV);
        result.setProperty("name", name);
        property.forEach(result::setProperty);
        result.save();
        return result;
    }

    private static String sl(String str) {
        String[] split = str.split("\n");
        List<String> collect = Arrays.stream(split).map(m -> {
            String[] split1 = m.split("\t");
            return String.format("\"income\":\"%s\",\"discount\":\"%s\",\"deduct\":\"%s\"",
                    split1[0], split1[1], split1[2]);
        }).collect(Collectors.toList());
        String join = StringUtils.join(collect, "},{");
        return "[{" + join + "]}";
    }

//    private static <T> T findByName(String name) {
//
//    }

    public static List<Map<String, Object>> executeQuery(String sql, ODatabaseSession db) {
        List<Map<String, Object>> result = new ArrayList<>();
        log.info("\n执行语句：{}", sql);
        OResultSet rs = db.query(sql);
        OResult next;
        while (rs.hasNext()) {
            next = rs.next();
            Map<String, Object> map = new HashMap<>();
            next.getIdentity().ifPresent(x -> map.put("rid", x));
            for (String propertyName : next.getPropertyNames()) {
                String k = propertyName.replaceAll("_", "");
                map.put(k, next.getProperty(propertyName));
            }
//                log.info("\n{}", map.toString());
            result.add(map);
        }
        return result;
    }
}
