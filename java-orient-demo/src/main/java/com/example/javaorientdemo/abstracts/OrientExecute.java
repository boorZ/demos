package com.example.javaorientdemo.abstracts;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/9 16:57
 */
@Slf4j
public abstract class OrientExecute<T> {
    public OrientExecute() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) t ;
        Class<T> c = (Class<T>) p.getActualTypeArguments()[0];
        System.out.println(c.getName());
    }
    //    protected IOrientExecuteService service;
//    protected String database;
//    public void getType() {
//        //Class<E> clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//        Type t = getClass().getGenericSuperclass();
//        ParameterizedType p = (ParameterizedType) t ;
//        Class<T> c = (Class<T>) p.getActualTypeArguments()[0];
//        System.out.println(c.getName());
//    }

//    public void init(IOrientExecuteService service, String database) {
//        this.service = service;
//        this.database = database;
//    }
//    public abstract T findByRid(String rid);
}
