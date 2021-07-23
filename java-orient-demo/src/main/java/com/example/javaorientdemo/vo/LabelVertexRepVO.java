package com.example.javaorientdemo.vo;

import lombok.Data;

/**
 * 图数据——顶点
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/3 21:43
 */
@Data
public class LabelVertexRepVO {
    /** id **/
    private String rid;
    /** 标签ID **/
    private Long labelId;
    /** 标签名称 **/
    private String labelName;
    /** 标签类型 **/
    private String labelType;
    /** 层级 **/
    private String outLabelEdge;
}
