package com.fyp.fly.services.user.controllers;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author fyp
 * @crate 2019/3/1 22:53
 * @project fly
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/article/{id}")
    public String findArticleById(@PathVariable Long id){
        return restTemplate.getForObject("http://fly-article-service/article/"+id,String.class);
    }
}
