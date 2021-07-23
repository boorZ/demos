package com.example.springbootdatademo;

import com.example.springbootdatademo.utils.C3P0Utils;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/5/26 16:40
 */
@Slf4j
public class LawInsertDemo {

    protected static String INSERT_LAW = "INSERT INTO `t_doc_law`(`doc_law_id`, `doc_no`, `law_full_name`, `law_name`, `law_info`, `writ_no`, `dispatch_unit`, " +
            "`writ_no_type`, `dispatch_time`, `valid_time`, `invalid_time`, `law_status`, `law_type`, `is_show_directory`, `from_structuration`, `is_enable`, `full_html`, " +
            "`full_text`, `create_user`, `create_time`, `modify_user`, `modify_time`, `law_name_words`, `law_full_name_words`, `structured`, `tenant_id`) VALUES ";

    protected static String INSERT_INFO = "INSERT INTO `t_doc_info`(`doc_id`, `doc_type`, `doc_status`, `hots`, `views`, `reading_volume`, `datails_data`, `es_data`, `is_release`, `is_enable`, `create_user`, `create_time`, `modify_user`, `modify_time`) VALUES ";
    protected static String INSERT_LAW_MONGO_INFO = "db.getCollection(\"t_doc_law\").insertMany([";

    protected static String GENERATE_PATH = "D:" + File.separator + "filepath" + File.separator;

    protected static String LAW_PATH = "law" + File.separator;
    protected static String LAW_MONGO_PATH = "law_mongo" + File.separator;
    protected static String INFO_PATH = "info" + File.separator;

    {
        try {
            verifyFilePath(GENERATE_PATH);
            verifyFilePath(GENERATE_PATH + LAW_PATH);
            verifyFilePath(GENERATE_PATH + LAW_MONGO_PATH);
            verifyFilePath(GENERATE_PATH + INFO_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGenerate() {
        generateSql(0, 10);
    }

    @Test
    public void generate() throws IOException {
        // 共多少条数据     每次读取X条数据   一共X页
        int total = 61273, pageSize = 1000, totalPage = total / pageSize + (total % pageSize == 0 ? 0 : 1);

        for (int i = 0; i < totalPage; i++) {
            generateSql(i * pageSize, pageSize);
        }
        log.info("\nlaw文件生成路径：{}", GENERATE_PATH + LAW_PATH);
        log.info("\nlaw_mongo文件生成路径：{}", GENERATE_PATH + LAW_MONGO_PATH);
        log.info("\ninfo文件生成路径：{}", GENERATE_PATH + INFO_PATH);
    }

    public static void verifyFilePath(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.isReadable(path)) {
            Files.createDirectory(path);
        }
    }

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
        try {
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                writer.write(content);
            }
        } catch (Exception ignored) {
        }
        //追加写模式
//        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)){
//            writer.write("Hello World -字母哥!!");
//        }
    }

    public void generateSql(Integer index, Integer size) {
        String sql = "SELECT doc_law_id,doc_no,law_full_name,law_name,law_info,writ_no,dispatch_unit,writ_no_type,dispatch_time,valid_time,invalid_time,law_status,law_type,is_show_directory,is_enable,full_html,full_text " +
                "FROM demo.`t_doc_law` limit " + index + ", " + size;
        ResultSet rs = null;

        StringBuilder lawSb = new StringBuilder(INSERT_LAW);
        StringBuilder infoSb = new StringBuilder(INSERT_INFO);
        StringBuilder lawMongoSb = new StringBuilder(INSERT_LAW_MONGO_INFO);

//        lawSb.append(INSERT_LAW);
//        infoSb.append(INSERT_INFO);
//        lawMongoSb.append(INSERT_LAW_MONGO_INFO);

        try {
            rs = C3P0Utils.getConnection(sql);
            while (rs.next()) {
                String docLawId = field(rs, "doc_law_id");
                String lawStatus = rs.getString("law_status");
                String docNo = field(rs, "doc_no");
                String lawFullName = field(rs, "law_full_name");
                String lawName = field(rs, "law_name");
                String lawInfo = field(rs, "law_info");
                String writNo = field(rs, "writ_no");
                String dispatch_unit = field(rs, "dispatch_unit");
                String writ_no_type = field(rs, "writ_no_type");
                String dispatch_time = field(rs, "dispatch_time");
                String valid_time = field(rs, "valid_time");
                String invalid_time = field(rs, "invalid_time");
                String law_type = field(rs, "law_type");
                String is_show_directory = field(rs, "is_show_directory");
                String full_html = field(rs, "full_html");
                String full_text = field(rs, "full_text");

                lawStatus = StringUtils.isEmpty(lawStatus) ? "3" : lawStatus;
                lawStatus = field(lawStatus);
                String status = field("Y");

                String createUser = field("390400352106590208");
                String modifyUser = field("283404838765596672");
                String createTime = field("2021-05-28 10:10:10");

                String lawSql = String.format("(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)",
                        docLawId, docNo, lawFullName, lawName, lawInfo, writNo, dispatch_unit, writ_no_type,
                        dispatch_time, valid_time, invalid_time, lawStatus, law_type, is_show_directory,
                        null, status, full_html, full_text, createUser, createTime, modifyUser, createTime,
                        "\"" + removeSpecialChar(rs.getString("law_name")) + "\"",
                        "\"" + removeSpecialChar(rs.getString("law_full_name")) + "\"",
                        "0", "0");

                String infoSql = String.format("(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)",
                        docLawId, 3, 0, 0, 0, 0, null, null, status, status, createUser, createTime, modifyUser, createTime);

                String lawMongoSql = String.format("{docLawId: NumberLong(%s), " +
                                "docNo: %s, lawFullName: %s, lawName: %s, lawInfo: %s, " +
                                "writNo: %s, dispatchUnit: %s, writNoType: %s, " +
                                "dispatchTime: ISODate(%s), validTime: ISODate(%s), " +
                                "invalidTime: ISODate(%s), lawStatus: %s, lawType: %s, " +
                                "isShowDirectory: %s, isEnable: %s, fullHtml: %s, " +
                                "fullText: %s, createUser: NumberLong(%s), createTime: ISODate(%s), " +
                                "modifyUser: null, modifyTime: null}",
                        docLawId, docNo, lawFullName, lawName, lawInfo, writNo, dispatch_unit,
                        writ_no_type, dispatch_time, valid_time, invalid_time, lawStatus,
                        law_type, is_show_directory, status, deleteLastLine(rs.getString("full_html")),
                        deleteLastLine(rs.getString("full_text")), createUser, createTime);

                lawSb.append(lawSql).append(",");
                infoSb.append(infoSql).append(",");
                lawMongoSb.append(lawMongoSql).append(",");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                C3P0Utils.close();
            } catch (Exception ignored) {
            }
        }
        lawMongoSb.append("]);");

        String laws = lawSb.toString();
        String infos = infoSb.toString();
        String mongos = lawMongoSb.toString();

        laws = laws.substring(0, (laws.length() - 0x1));
        infos = infos.substring(0, (infos.length() - 0x1));
        mongos = mongos.substring(0, (mongos.length() - 0x1));

        generateFile(String.format("%slaw_mongo_%s-%s.sql", LAW_MONGO_PATH, index, size), mongos);
        generateFile(String.format("%slaw_%s-%s.sql", LAW_PATH, index, size), laws);
        generateFile(String.format("%sinfo_%s-%s.sql", INFO_PATH, index, size), infos);
    }

    private String field(ResultSet rs, String fieldName) throws SQLException {
        String str = rs.getString(fieldName);
        if (str == null) return null;
        return field(str);
    }

    private String deleteLastLine(String text) {
        Pattern pattern = Pattern.compile("<p.*</p>");
        Matcher matcher = pattern.matcher(text);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(matcher.group(0));
        }
        return field(result.toString());
    }

    private String field(String fieldName) {
        fieldName = fieldName.replaceAll("\"", "\\\\\"");
        return "\"" + fieldName + "\"";
    }

    public static String removeSpecialChar(String text) {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa50-9a-zA-Z]+");
        Matcher matcher = pattern.matcher(text);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(matcher.group(0));
        }
        return result.length() > 0 ? result.toString() : null;
    }
}
