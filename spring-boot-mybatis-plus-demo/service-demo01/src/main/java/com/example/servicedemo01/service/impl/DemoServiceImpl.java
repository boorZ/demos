package com.example.servicedemo01.service.impl;

import com.example.servicedemo01.service.IDemoService;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/25 15:52
 */
public class DemoServiceImpl<T> implements IDemoService<T> {

    @Override
    public void init() {
        System.out.println("Demo实现类");
    }
}
