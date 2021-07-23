package com.example.servicedemo01;

import com.example.servicedemo01.service.IDemoService;
import com.example.servicedemo01.service.impl.DemoServiceImpl;
import com.example.servicedemo01.vo.bo.TestBo;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/25 15:52
 */
public class Main {
    public static void main(String[] args) {
//        TestBo testBo = new TestBo("1", "2");
        IDemoService<TestBo> service = new DemoServiceImpl<>();
        service.init();
    }

}
