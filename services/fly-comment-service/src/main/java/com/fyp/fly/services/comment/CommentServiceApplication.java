package com.fyp.fly.services.comment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author fyp
 * @crate 2019/2/28 21:29
 * @project fly
 */
@SpringBootApplication
@EnableFeignClients
@MapperScan("com.fyp.fly.services.comment.repository.mapper")
public class CommentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentServiceApplication.class,args);
    }
}
