package com.example.noaskqademo.vo;

import lombok.Data;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/25 10:13
 */
@Data
public class QaBO {
    private Long docId;
    private String docName;
    private String fullText;
}
