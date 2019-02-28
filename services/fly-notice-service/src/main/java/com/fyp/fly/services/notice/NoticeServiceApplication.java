package com.fyp.fly.services.notice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author fyp
 * @crate 2019/2/28 21:30
 * @project fly
 */
@SpringBootApplication
@EnableEurekaClient
public class NoticeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NoticeServiceApplication.class,args);
    }
}
