package com.example.designdemo.demo1;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;

/**
 * @author zhou lin
 * @description 优化实现 计算器 扩展
 * @create 2021-07-21 00:32
 */
public class OptimizeMainExtends {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
//        OperationExtends operationExtends = new OperationAddExtends();
        OperationExtends operationExtends = OperationFactoryExtends.createOperation("sub");
        operationExtends.setParameter(OperationBo.builder().valueA(1d).valueB(2d).builder());
        double count = operationExtends.count();
        System.out.println("结果：" + count);
    }

}

class OperationFactoryExtends {
    public static OperationExtends createOperation(String serviceName) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        OperationExtends operationExtends = null;
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
            String name = annotation.name();
            if (!serviceName.equals(name)) continue;
            operationExtends = (OperationExtends) aClass.newInstance();
        }
        return operationExtends;
    }
}
//interface IOperationExtends {
//    public void setParameter(OperationBo operationBo);
//    public double count();
//}

abstract class OperationExtends{
    public abstract void setParameter(OperationBo operationBo);
    public abstract double count();
}
@MyAnno(name = "add")
class OperationAddExtends extends OperationExtends {
    OperationBo operationBo;
    @Override
    public void setParameter(OperationBo operationBo) {
        this.operationBo = operationBo;
    }

    @Override
    public double count() {
        return operationBo.getValueA() + operationBo.getValueB();
    }
}
@MyAnno(name = "sub")
class OperationSubExtends extends OperationExtends {
    OperationBo operationBo;
    @Override
    public void setParameter(OperationBo operationBo) {
        this.operationBo = operationBo;
    }

    @Override
    public double count() {
        return operationBo.getValueA() - operationBo.getValueB();
    }
}
