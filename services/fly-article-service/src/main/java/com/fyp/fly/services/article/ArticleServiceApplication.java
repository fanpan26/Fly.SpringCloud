package com.fyp.fly.services.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author fyp
 * @crate 2019/2/28 21:00
 * @project fly
 */
@SpringBootApplication
@EnableFeignClients
@MapperScan("com.fyp.fly.services.article.repository.mapper")
public class ArticleServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArticleServiceApplication.class, args);
    }
}
