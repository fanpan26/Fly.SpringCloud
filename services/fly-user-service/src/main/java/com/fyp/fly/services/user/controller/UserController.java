package com.fyp.fly.services.user.controller;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fyp
 * @crate 2019/3/1 22:53
 * @project fly
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public JsonResult getUserInfoById(@PathVariable Long id){
        return userService.getUserInfo(id);
    }
}
