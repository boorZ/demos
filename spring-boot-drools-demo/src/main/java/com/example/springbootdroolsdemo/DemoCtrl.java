package com.example.springbootdroolsdemo;

import com.rules.pojo.RulePOJO;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/5/17 15:49
 */
@RestController
public class DemoCtrl {
    final KieContainer kieContainer;

    public DemoCtrl(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    /*暴露出来的api接口，通过捕获type=进行后续规则*/
    @GetMapping(value = "/test")
    public RulePOJO getQuestions(@RequestParam String searchValue) {
//        RulePOJO rule = RulePOJO.builder()
//                .mc(searchValue)
//                .sz(searchValue)
//                .build();
        RulePOJO rule = new RulePOJO();
        rule.setMc(searchValue);
        rule.setSz(searchValue);

        KieSession kieSession = kieContainer.newKieSession("suit_me_rule");
        kieSession.insert(rule);
        kieSession.fireAllRules();
        kieSession.dispose();
        return rule;
    }
}
