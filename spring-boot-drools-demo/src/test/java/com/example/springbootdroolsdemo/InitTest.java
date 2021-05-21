package com.example.springbootdroolsdemo;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/5/14 11:53
 */
public class InitTest {
    String path = "D:\\code\\my\\demos\\spring-boot-drools-demo\\src\\main\\resources\\";

    @Test
    public void checkDrl() throws FileNotFoundException {
        File file = new File(path + "drools/drools_test.xlsx");
        InputStream is = new FileInputStream(file);
        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        String drl = compiler.compile(is, InputType.XLS);
        System.out.println(drl);
    }
    @Test
    public void checkDrl2() {
        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        String drl = compiler.compile(ResourceFactory.newClassPathResource("drools/drools_test.xlsx"), InputType.XLS);
        System.out.println(drl);
    }
    private final KieContainer kieContainer;

    @Autowired
    public InitTest(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }
    @Test
    public void testDecision(){
        KieSession kieSession = kieContainer.newKieSession("decision-rules");
//        KieSession kieSession = this.getKieSessionBySessionName("decision-rules");

        kieSession.fireAllRules();
        kieSession.dispose();
    }


    @Test
    public void log() {
        double v = 1 + (8052 - 8 + 0.5) / (8 + 0.5);
        double log1 = Math.log10(v);
        double log = log(v, 10);
        System.out.println("log1: " + log1);
        System.out.println("log: " + log);
    }

    /**
     * 换底公式
     * @param value
     * @param base
     * @return
     */
    static public double log(double value, double base) {
        return Math.log(value) / Math.log(base);
    }

}
