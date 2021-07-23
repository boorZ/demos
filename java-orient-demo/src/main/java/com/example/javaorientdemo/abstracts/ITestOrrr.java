package com.example.javaorientdemo.abstracts;

import java.lang.reflect.ParameterizedType;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/9 20:11
 */
public abstract class ITestOrrr<T> {
    public Class<T> getGenericType() {
        Class<T> clz = (Class<T>) (((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        return clz;
    }
}
