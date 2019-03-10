package com.fyp.fly.services.user.controllers;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.services.user.domain.UserInfo;
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
@RequestMapping("/user")
public class UserController {


    @GetMapping("/{id}")
    public JsonResult getUserInfoById(@PathVariable Long id){
        return ResultUtils.success(new UserInfo());
    }
}
