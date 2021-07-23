package com.example.noaskqaes.service;

import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/7/2 18:12
 */
public abstract class Dsl {
    abstract SearchSourceBuilder dsl();
}
