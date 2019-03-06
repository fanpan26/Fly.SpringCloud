package com.fyp.fly.services.account.services;

import com.fyp.fly.common.api.result.JsonResult;

public interface AccountService {
    /**
     * 用户登录接口，用于 fly-sso 调用
     *
     * @param loginName 用户名
     * @param loginPwd  密码
     * @return 登录成功：返回用于web站点校验的ticket 登录失败：账号不存在/密码错误
     */
    JsonResult login(String loginName, String loginPwd);

    /**
     * 根据JWT来校验是否登录，并返回应用端的ticket
     * */
    JsonResult generateTicket(String token);
}
