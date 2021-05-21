package com.rules.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/5/17 15:18
 */
@Data
//@Builder
public class RulePOJO {
    /** 公司名称 **/
    private String mc;
    /** 纳税人资质 **/
    private String zz;
    /** 小微企业 **/
    private String qy;
    /** 企业类型 **/
    private String qylx;
    /** 行业分类 **/
    private String hy;
    /** 营业范围 **/
    private String yyfw;
    /** 预判税种 **/
    private String sz;
    private List<String> data = new ArrayList<>();
}
