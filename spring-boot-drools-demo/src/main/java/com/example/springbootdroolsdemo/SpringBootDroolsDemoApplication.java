package com.example.springbootdroolsdemo;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootDroolsDemoApplication {
    /** 需要注入这个类到bean工厂 **/
    @Bean
    public KieContainer kieContainer() {
        return KieServices.Factory.get().getKieClasspathContainer();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDroolsDemoApplication.class, args);
    }

}
