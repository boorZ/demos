package com.example.javaorientdemo.abstracts.impl;

import com.example.javaorientdemo.abstracts.ITestOrrr;

import java.lang.reflect.*;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/9 20:09
 */
public class TestOrrr<T> extends ITestOrrr<T> {
    public Class getGenericSuperclassBounds(Class childClass){
        Type type = childClass.getGenericSuperclass();
        while(!(type instanceof Class)){
            if(type instanceof WildcardType){
                type = ((WildcardType)type).getUpperBounds()[0];
            }
            else if(type instanceof TypeVariable<?>){
                type = ((TypeVariable<?>)type).getBounds()[0];
            }
            else if(type instanceof ParameterizedType){
                ParameterizedType ptype = (ParameterizedType)type;
                Type[] types = ptype.getActualTypeArguments();
                if(types==null||types.length==0){
                    return Object.class;
                }
                if(types.length>1){
                    throw new RuntimeException(childClass.getName()+"继承的泛型"+ptype+"的实参数量多于1个");
                }
                type = ptype.getActualTypeArguments()[0];
            }
            else if(type instanceof GenericArrayType){
                type = ((GenericArrayType)type).getGenericComponentType();
            }
        }
        return (Class)type;
    }
    public TestOrrr() {
//        Class<T> clz = (Class<T>) getSuperClassGenricType(this.getClass());
//        System.out.println(clz);
        Class genericSuperclassBounds = getGenericSuperclassBounds(this.getClass());

        Class<T> superclass = (Class<T>)this.getClass().getSuperclass();
        Type genericSuperclass = superclass.getGenericSuperclass();
        ParameterizedType p = (ParameterizedType)genericSuperclass;
        Type[] actualTypeArguments = p.getActualTypeArguments();
        System.out.println();
    }

    /**
     * 通过反射,获得定义Class时声明的父类的泛型参数的类型. 如无法找到,返回Object.class
     */
    public Class<Object> getSuperClassGenricType(final Class clz) {
        Type type = clz.getGenericSuperclass();
        if(!(type instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params =((ParameterizedType)type).getActualTypeArguments();
        if(!(params[0] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[0];
    }

}
