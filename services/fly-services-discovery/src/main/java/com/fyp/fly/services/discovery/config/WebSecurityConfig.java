package com.fyp.fly.services.discovery.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 配置Eureka Server 用户认证，如果不加这个配置，Eureka Client无法注册成功。
 * */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf
        http.csrf().ignoringAntMatchers("/eureka/**");
        //开启认证
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

}