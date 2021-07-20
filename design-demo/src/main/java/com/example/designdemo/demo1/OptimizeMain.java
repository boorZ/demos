package com.example.designdemo.demo1;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author zhou lin
 * @description 优化实现 计算器
 * @create 2021-07-20 20:31
 */
public class OptimizeMain {

    public static void main(String[] args) {
        Class<Operation> clazz = Operation.class;
        int modifiers = clazz.getModifiers();
        boolean isAbstract = Modifier.isAbstract(modifiers);
//        boolean isInterface = Modifier.isInterface(modifiers);
        Field[] fields = clazz.getFields();
        Field[] declaredFields = clazz.getSuperclass().getDeclaredFields();

        System.out.println();
//        OperationBo operationBo = OperationBo.builder()
//                .valueA(1d)
//                .valueB(2d)
//                .builder();
//        Operation operation = new OperationMul(operationBo);

//        Operation operation = OperationFactory.createOperation(OperationEnum.ADD,
//                OperationBo.builder()
//                        .valueA(1d)
//                        .valueB(2d)
//                        .builder());
//        double count = operation.count();
//        System.out.println("结果：" + count);
    }
}

/**
 * 操作枚举
 **/
enum OperationEnum {
    /**
     * 加
     **/
    ADD,
    /**
     * 减
     **/
    SUB,
    /**
     * 乘
     **/
    MUL,
    /**
     * 除
     **/
    DIV
}

/**
 * 操作工厂（静态工厂）
 **/
class OperationFactory {
    public static Operation createOperation(OperationEnum operationEnum, OperationBo operationBo) {
        switch (operationEnum) {
            case ADD:
                return new OperationAdd(operationBo);
            case SUB:
                return new OperationSub(operationBo);
            case MUL:
                return new OperationMul(operationBo);
            case DIV:
                return new OperationDiv(operationBo);
            default:
                throw new RuntimeException("请选择正确的操作类型");
        }
    }
}

/**
 * 操作对象
 **/
class OperationBo {
    private double valueA;
    private double valueB;

    public static OperationBo.Builder builder() {
        return new OperationBo.Builder();
    }

    private OperationBo(Builder builder) {
        this.valueA = builder.valueA;
        this.valueB = builder.valueB;
    }

    public static class Builder {
        private double valueA;
        private double valueB;

        public Builder valueA(double valueA) {
            this.valueA = valueA;
            return this;
        }

        public Builder valueB(double valueB) {
            this.valueB = valueB;
            return this;
        }

        public OperationBo builder() {
            return new OperationBo(this);
        }
    }

    public double getValueA() {
        return valueA;
    }

    public double getValueB() {
        return valueB;
    }

    @Override
    public String toString() {
        return "OperationBo{" +
                "valueA=" + valueA +
                ", valueB=" + valueB +
                '}';
    }
}

/**
 * 抽象 操作-计算
 **/
abstract class Operation {
    public OperationBo operationBo;

    public Operation(OperationBo operationBo) {
        this.operationBo = operationBo;
    }

    public abstract double count();
}

/**
 * 抽象 实现-算法-加
 **/
//@MyAnno(name = "add")
class OperationAdd extends Operation {
    public OperationAdd(OperationBo operationBo) {
        super(operationBo);
    }

    @Override
    public double count() {
        return operationBo.getValueA() + operationBo.getValueB();
    }
}

/**
 * 抽象 实现-算法-减
 **/
//@MyAnno(name = "sub")
class OperationSub extends Operation {
    public OperationSub(OperationBo operationBo) {
        super(operationBo);
    }

    @Override
    public double count() {
        return operationBo.getValueA() - operationBo.getValueB();
    }
}

/**
 * 抽象 实现-算法-乘
 **/
//@MyAnno(name = "mul")
class OperationMul extends Operation {
    public OperationMul(OperationBo operationBo) {
        super(operationBo);
    }

    @Override
    public double count() {
        return operationBo.getValueA() * operationBo.getValueB();
    }
}

/**
 * 抽象 实现-算法-除
 **/
//@MyAnno(name = "div")
class OperationDiv extends Operation {
    public OperationDiv(OperationBo operationBo) {
        super(operationBo);
    }

    @Override
    public double count() {
        if (operationBo.getValueB() == 0) {
            throw new RuntimeException("除数不能为0");
        }
        return operationBo.getValueA() / operationBo.getValueB();
    }
}

