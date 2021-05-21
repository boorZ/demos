package com.example.springbootdroolsdemo.utils;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/5/20 17:58
 */
public class DroolsUtils {
    static String PATH = "D:\\code\\my\\demos\\spring-boot-drools-demo\\src\\main\\resources\\drools\\";
    /**
     * 解析决策表格式是否正确
     * 正确返回DRL文件
     * 错误返回 NULL字符串
     *
     * @param filePath 文件存储地址
     * @param fileType 文件类型  XLS 和 CSV 两种
     * @return 返回DRL文件内容
     */
    public static String isValidFile(String filePath, InputType fileType) {
        long staTime = System.currentTimeMillis();
        String drl = null;
        try {
            File file = new File(PATH + filePath);
            InputStream is = new FileInputStream(file);
            // DRL 解析类 ：compile 方法解析文件 可以解析 XLS 和 CSV 两种文件格式
            SpreadsheetCompiler conCompiler = new SpreadsheetCompiler();
            drl = conCompiler.compile(is, fileType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("解析决策表：" + filePath + (staTime - System.currentTimeMillis()) + "毫秒");
        return drl;
    }

    /**
     * 执行drl文件并返回结果集
     *
     * @param drl drl文件内容
     * @param obj 所需执行的对象
     * @return 返回对象执行后的结果
     */
    public static <T> T executeDrl(String drl, T t) {
        return baseExecute(drl, t, null);
    }

    public static <T> T executeDrl(String drl, T t, int size) {
        return baseExecute(drl, t, size);
    }

    private static <T> T baseExecute(String drl, T t, Integer size) {
        long staTime = System.currentTimeMillis();
        int i = 0;
        if (drl != null) {
            KieHelper helper = new KieHelper();
            helper.addContent(drl, ResourceType.DRL);
            KieSession kieSession = helper.build().newKieSession();
            kieSession.insert(t);
            // 执行所有规则
            if (size == null || size == 0) {
                i = kieSession.fireAllRules();
            } else {
                i = kieSession.fireAllRules(size);
            }
            kieSession.dispose();
        } else {
            System.out.println("文件解析错误，请检查决策表");
        }
        System.out.println(String.format("执行了%s次, %s毫秒", i, (staTime - System.currentTimeMillis())));
        return t;
    }
}
