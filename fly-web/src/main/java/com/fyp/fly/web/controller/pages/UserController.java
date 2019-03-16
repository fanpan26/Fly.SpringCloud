package com.fyp.fly.web.controller.pages;

import com.fyp.fly.common.dto.FlyUserDto;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.web.clients.user.UserApiClient;
import com.fyp.fly.web.config.FlyContext;
import com.fyp.fly.web.controller.biz.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author fyp
 * @crate 2019/3/13 21:30
 * @project fly
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController{

    @Autowired
    private UserApiClient userApiClient;

    @GetMapping("/home")
    public String home() throws Exception {
        JsonResult<FlyUserDto> userRes = userApiClient.getUserById(FlyContext.getUserId());
        if (ResultUtils.isSuccess(userRes)) {
            request.setAttribute("homeUser",userRes.getData());
        }
        return "/user/home";
    }
}
