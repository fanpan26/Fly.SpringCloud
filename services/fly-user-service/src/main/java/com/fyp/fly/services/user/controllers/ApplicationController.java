package com.fyp.fly.services.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/2/28 22:13
 * @project fly
 */
@RestController
@RequestMapping("/api")
public class ApplicationController {

    @Value("${spring.application.name}")
    private String serviceId;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 获取用户服务的详细信息
     * */
    @GetMapping("/user-service")
    public List<ServiceInstance> userServiceInfo(){
       return this.discoveryClient.getInstances(serviceId);
    }
}
