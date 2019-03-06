package com.fyp.fly.services.account.controllers;

import com.fyp.fly.common.api.result.JsonResult;

import com.fyp.fly.services.account.services.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fyp
 * @crate 2019/3/5 21:50
 * @project fly
 */
@Api(description = "用户账户接口")
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * @param loginName 用户名
     * @param loginPwd  用户密码
     * @desc 用户登录接口
     * @return userId or error msg
     * @author fyp
     * */
    @ApiOperation(value = "用户登录" ,  notes="用户登录")
    @PostMapping("/login")
    public JsonResult login(@RequestParam("name") String loginName, @RequestParam("pwd") String loginPwd) {
        return accountService.login(loginName, loginPwd);
    }
}
