package com.fyp.fly.services.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fyp
 * @crate 2019/2/28 21:00
 * @project fly
 * 可以不用加 @EnableEurekaClient
 * 文档参考：https://cloud.spring.io/spring-cloud-netflix/single/spring-cloud-netflix.html#netflix-eureka-client-starter
 */
@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class,args);
    }
}
