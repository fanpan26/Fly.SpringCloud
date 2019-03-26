package com.fyp.fly.web.config;

import com.fyp.fly.web.interceptors.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author fyp
 * @crate 2019/3/10 23:21
 * @project fly
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor interceptor;

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
                .excludePathPatterns("/jie/hot")
                .excludePathPatterns("/rank")
                .excludePathPatterns("/res/css/**")
                .excludePathPatterns("/res/images/**")
                .excludePathPatterns("/res/mods/**")
                .excludePathPatterns("/res/layui/**");
    }
}
