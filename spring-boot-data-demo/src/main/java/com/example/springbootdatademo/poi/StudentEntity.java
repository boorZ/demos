package com.example.springbootdatademo.poi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/7/5 14:56
 */
@Data
@ExcelTarget("student")
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity implements Serializable {
    /**
     * id
     */
    private Integer id;
    /**
     * 学生姓名
     */
    @Excel(name = "学生姓名", isImportField = "true_st")
    private String name;
    /**
     * 学生性别
     */
    @Excel(name = "学生性别", replace = {"男_1", "女_2"}, suffix = "生", isImportField = "true_st")
    private int sex;

    @Excel(name = "出生日期", format = "yyyy-MM-dd", isImportField = "true_st")
    private Date birthday;
    /**
     * 学生姓名
     */
    @Excel(name = "地址", height = 20, width = 30, isImportField = "true_st")
    private String site;

}
