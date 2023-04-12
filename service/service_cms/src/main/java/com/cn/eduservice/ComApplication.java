package com.cn.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Project: online_education
 * @Package: PACKAGE_NAME
 * @Description:
 */
@SpringBootApplication
@ComponentScan("com.cn")
public class ComApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComApplication.class, args);
    }
}
