package com.example.springbootdroolsdemo;

import com.example.springbootdroolsdemo.utils.DroolsUtils;
import com.rules.pojo.RulePOJO;
import org.drools.decisiontable.InputType;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/5/17 10:02
 */
public class DroolsDemo {


    public static void main(String[] args) {
        long staTime = System.currentTimeMillis();
        // 适合我的表单
        String path = "suitme_from.xlsx";
        String drl = DroolsUtils.isValidFile(path, InputType.XLS);
//        System.out.println("xlsx: \n" + drl);
        RulePOJO rulePojo = new RulePOJO();
//        rulePojo.setMc("哈哈");
        rulePojo.setSz("车船税");
        RulePOJO result = DroolsUtils.executeDrl(drl, rulePojo);
        System.out.println("result: " + result + "\n" + (staTime - System.currentTimeMillis()) + "毫秒");
    }

    @Test
    public void ruleTest() {
        KieContainer kieContainer = (KieContainer) SpringContextUtil.getBean("kieContainerImpl");

        KieSession kieSession = kieContainer.newKieSession("suit_me_rule");
        kieSession.fireAllRules();
        kieSession.dispose();
    }

}
