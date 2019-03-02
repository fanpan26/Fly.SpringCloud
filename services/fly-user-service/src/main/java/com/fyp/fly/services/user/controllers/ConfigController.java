package com.fyp.fly.services.user.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fyp
 * @crate 2019/3/2 23:33
 * @project fly
 */
@RestController
@RequestMapping("/config")
public class ConfigController {


    @Value("${git.config.name}")
    private String configName;


    @GetMapping("/name")
    public String getConfigByConfigServer(){
        return "this is from config server:"+configName;
    }
}
