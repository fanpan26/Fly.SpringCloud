package com.fyp.fly.web.controllers.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @RequestMapping("/test")
    public String layout(){
        return "test";
    }
}
