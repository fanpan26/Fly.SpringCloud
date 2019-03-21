package com.fyp.fly.services.user.controller;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/3/1 22:53
 * @project fly
 */
@Api(description = "用户账户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/{id}")
    public JsonResult getUserInfoById(@PathVariable Long id){
        return userService.getUserInfo(id);
    }

    @ApiOperation(value = "获取用户列表")
    @PostMapping("/list")
    public JsonResult getUserList(@RequestParam("userIds") List<String> userIds) {
        return userService.getUserList(userIds);
    }
}
