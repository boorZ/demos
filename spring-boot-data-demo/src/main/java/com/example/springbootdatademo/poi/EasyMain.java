package com.example.springbootdatademo.poi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/7/5 14:57
 */
public class EasyMain {

    List<StudentEntity> list;

    protected String PATH = "D:\\data\\excel\\";
    @Before
    public void initData() {
        list = new ArrayList<>();
        Date date = new Date();
        list.add(new StudentEntity(1, "百度1", 1, date, "北京市海淀区西北旺东路10号院百度科技园1号楼"));
        list.add(new StudentEntity(2, "阿里巴巴", 2, date, "北京市海淀区西北旺东路10号院百度科技园1号楼"));
        list.add(new StudentEntity(3, "Lemur", 1, date, "亚马逊热带雨林"));
        list.add(new StudentEntity(4, "一众", 2, date, "山东济宁俺家"));


    }
    
    @Test
    public void test01() throws IOException {
//        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
//                StudentEntity .class, list);

//        File savefile = new File(PATH);
//        if (!savefile.exists()) {
//            savefile.mkdirs();
//        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"), StudentEntity.class, list);
        FileOutputStream fos = new FileOutputStream(PATH + "StudentEntity.xls");
        workbook.write(fos);
        fos.close();
    }
}
