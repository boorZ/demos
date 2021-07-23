package com.example.javaorientdemo.vo;

import lombok.Data;

/**
 * 图数据——边
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/3 21:43
 */
@Data
public class LabelEdgeRepVO {
    /** id **/
    private String rid;
    /** 边名称 **/
    private String name;
    /** 层级 **/
    private Integer tier;
    /** 起始 顶点ID **/
    private String in;
    /** 端点 顶点ID **/
    private String out;
}
