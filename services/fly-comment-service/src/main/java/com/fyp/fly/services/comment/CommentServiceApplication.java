package com.fyp.fly.services.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author fyp
 * @crate 2019/2/28 21:29
 * @project fly
 */
@SpringBootApplication
@EnableEurekaClient
public class CommentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentServiceApplication.class,args);
    }
}
