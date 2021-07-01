package com.cdzq.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.cdzq.study.mapper")
@SpringBootApplication
@EnableScheduling
public class StudyApplication{

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }

}
