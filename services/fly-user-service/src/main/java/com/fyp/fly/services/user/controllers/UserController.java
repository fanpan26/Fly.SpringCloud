package com.fyp.fly.services.user.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fyp
 * @crate 2019/2/28 22:13
 * @project fly
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/user")
    public String getUser(){
        return "this is user service";
    }
}
