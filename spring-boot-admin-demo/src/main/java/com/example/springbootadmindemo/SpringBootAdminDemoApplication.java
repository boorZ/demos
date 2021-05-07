package com.example.springbootadmindemo;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class SpringBootAdminDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminDemoApplication.class, args);
    }

}
