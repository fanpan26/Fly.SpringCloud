package com.fyp.fly.services.discovery.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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