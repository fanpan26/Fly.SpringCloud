package com.fyp.fly.web.controller.pages;

import com.fyp.fly.common.dto.UserModel;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.web.client.article.ArticleApiClient;
import com.fyp.fly.web.client.user.UserApiClient;
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

    @Autowired
    private ArticleApiClient articleApiClient;

    @GetMapping("/home")
    public String home() {
        //load userInfo
        JsonResult<UserModel> userRes = userApiClient.getUserById(FlyContext.getUserId());
        if (ResultUtils.isSuccess(userRes)) {
            request.setAttribute("homeUser",userRes.getData());
        }
        //load recently published
        JsonResult<Object> recentPublished = articleApiClient.getRecentPublishedByUserId();
        if (ResultUtils.isSuccess(recentPublished)){
            request.setAttribute("recentPublishedList",recentPublished.getData());
        }
        return "/user/home";
    }
}
