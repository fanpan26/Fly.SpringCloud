package com.fyp.fly.services.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
@RefreshScope
public class ApplicationController {

    private static final String SERVICE_ID = "fly-user-service";

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 获取用户服务的详细信息
     * */
    @GetMapping("/user-service")
    public List<ServiceInstance> userServiceInfo(){
       return this.discoveryClient.getInstances(SERVICE_ID);
    }
}
