package com.fyp.fly.services.account.controllers;

import com.fyp.fly.common.result.api.JsonResult;

import com.fyp.fly.services.account.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public JsonResult login(@RequestParam("name") String loginName, @RequestParam("pwd") String loginPwd) {
        return accountService.login(loginName, loginPwd);
    }

    /**
     * 用户退出
     * */
    @PostMapping("/logout")
    public JsonResult logout(String token){
        return accountService.logout(token);
    }

    @ApiOperation(value = "根据token生成ticket")
    /**
     * @param token 待生成ticket的token
     * @return 返回ticketResult
     * */
    @PostMapping("/ticket")
    public JsonResult generateTicket(String token){
        return accountService.generateTicket(token);
    }
    @ApiOperation(value = "校验ticket是否合法")
    /**
     * @param ticket 待校验的ticket
     * @return 返回 校验结果 -1 无效的ticket 0 ticket 有效
     * */
    @PostMapping("/ticket/verify")
    public JsonResult verifyTicket(String ticket){
        return accountService.verifyTicket(ticket);
    }

    @GetMapping("/user")
    public JsonResult getUserByToken(String token){
        return accountService.getUser(token);
    }

    @GetMapping("/testZuul")
    public  String getZuulConfig(){
        return "zuul test";
    }

}
