package com.fyp.fly.sso.config;

import com.fyp.fly.sso.interceptors.GlobalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author fyp
 * @crate 2019/3/7 23:02
 * @project fly
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private GlobalInterceptor interceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/account/**")
                .excludePathPatterns("/res/css/**")
                .excludePathPatterns("/res/images/**")
                .excludePathPatterns("/res/layui/**");
    }
}
