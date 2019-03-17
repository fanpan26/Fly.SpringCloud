package com.fyp.fly.services.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fyp
 * @crate 2019/3/17 9:53
 * @project fly
 */
@SpringBootApplication
@MapperScan("com.fyp.fly.services.common.repository.mapper")
public class CommonServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonServiceApplication.class, args);
    }
}
