package com.example.designdemo.demo1;

import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhou lin
 * @description 解析注解
 * @create 2021-07-20 23:39
 */
public class AnnotationTool {
    /**
     * 获取打了Desc注解的字典属性列表
     * @param t 类
     * @return 字典属性列表
     */
    public static <T> List<MyAnnoBo> getAnnotationBoList(Class<T> c) {
        System.out.println(c);
        if (c == null) {
            return Collections.emptyList();
        }
        try {
            List<MyAnnoBo> fixedVoList = new LinkedList<>();
            Field[] fields = c.getDeclaredFields();
            if (fields.length == 0) {
                return fixedVoList;
            }
            T cls = c.newInstance();
            for (Field field : fields) {
                MyAnno desc = field.getAnnotation(MyAnno.class);
                if (desc != null) {
                    MyAnnoBo vo = new MyAnnoBo();
                    System.out.println("field.getName(): " + field.getName());
                    System.out.println("field.getType(): " + field.getType());
                    System.out.println("key: " + field.get(cls));
                    System.out.println("name: " + desc.name());
                    System.out.println("----------------");
//                    vo.setKey(field.getInt(cls));
//                    vo.setValue(desc.value());
//                    vo.setRemark(desc.remark());
                    fixedVoList.add(vo);
                }
            }
            return fixedVoList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Test
    public void getFixedVoListTest() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<String> allEntity = getAllEntity();

//        List<AnnotationBo> voList = AnnotationTool.getAnnotationBoList(DemoBo.class);
//        for (AnnotationBo vo : voList) {
//            System.out.println(vo.toString());
//        }
    }
    private List<String> getAllEntity() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<String> ret = new ArrayList<>();
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory();
        Resource[] resources = pathMatchingResourcePatternResolver.getResources("classpath*:com/example/designdemo/**/*.class");
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        for (Resource resource : resources) {
            MetadataReader reader = cachingMetadataReaderFactory.getMetadataReader(resource);
            String className = reader.getClassMetadata().getClassName();
            Class aClass = loader.loadClass(className);

            MyAnno annotation = AnnotationUtils.findAnnotation(aClass, MyAnno.class);
            if (annotation == null) continue;
            System.out.println("class: " + aClass);
            System.out.println("annotation: " + annotation);
//            System.out.println("annotation.name(): " + annotation.name());
            OperationExtends o = (OperationExtends) aClass.newInstance();
            System.out.println("aClass.newInstance(): " + aClass.newInstance());

//            OperationAddExtends o = (OperationAddExtends)aClass.newInstance();
//            System.out.println("o(): " + o);
//            int modifiers = aClass.getModifiers();
//            if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
//                continue;
//            }
//            List<AnnotationBo> voList = AnnotationTool.getAnnotationBoList(aClass);
            System.out.println();
            //to do someing
        }
        return null;
    }
}
