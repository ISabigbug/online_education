package com.cn.eduservice;

import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan("com.cn.eduservice.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
