package com.example.springbootdatademo.xlsx;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author 周林
 * @Description
 * @email prometheus@noask-ai.com
 * @date 2019/11/13 10:15
 */
public class excelUtils {
    @Test
    public void mainsd() {
        Workbook wb;
        List<Map<String,Object>> list = new ArrayList<>();
        String filePath = "C:\\Users\\Card\\Desktop\\doc\\高频业务文件对照表 2.0.xlsx";
        wb = readExcel(filePath);
        if(wb != null){
            //获取第一个sheet
            Sheet sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            int da = 0;
            for (int i = 3; i<rownum; i++) {
                Map<String, Object> map = new LinkedHashMap<>();
                //获取第一行
                Row row = sheet.getRow(i);
                Cell pName = row.getCell(0);
                if (!pName.toString().equals("")) {
                    da++;
                }
                map.put("id", da);
                map.put("pName", pName);
                map.put("name", row.getCell(1));
                map.put("url", row.getCell(2));
                list.add(map);
            }
        }
        System.out.println();
    }

    //读取excel
    private static Workbook readExcel(String filePath){
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is;
        try {
            is = new FileInputStream(filePath);
            if(Objects.equals(".xls", extString)){
                return new HSSFWorkbook(is);
            }else if(Objects.equals(".xlsx", extString)){
                return new XSSFWorkbook(is);
            }else{
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
