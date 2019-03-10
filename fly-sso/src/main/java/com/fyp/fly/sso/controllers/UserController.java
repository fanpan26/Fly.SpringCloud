package com.fyp.fly.sso.controllers;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.sso.api.client.AccountApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fyp
 * @crate 2019/3/11 0:10
 * @project fly
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AccountApiClient accountApiClient;

    @GetMapping("/info")
    public JsonResult getUser(String token) {
        return accountApiClient.getUser(token);
    }
}
