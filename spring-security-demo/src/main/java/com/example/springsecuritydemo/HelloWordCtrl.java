package com.example.springsecuritydemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/5/7 14:58
 */
@RestController
public class HelloWordCtrl {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
