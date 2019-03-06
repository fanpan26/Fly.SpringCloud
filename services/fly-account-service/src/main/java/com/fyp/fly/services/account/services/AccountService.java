package com.fyp.fly.services.account.services;

import com.fyp.fly.common.result.api.JsonResult;

/**
 * @author fanpan26
 * */
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
     * @param token 根据SSO存储的token重新生成一个ticket
     * @return 返回ticket result
     * */
    JsonResult generateTicket(String token);

    /**
     * 根据ticket来校验是否登录，并返回应用端的ticket
     * @param ticket 根据ticket 校验是否用户正在登录状态
     * @return 返回 是否校验通过
     * */
    JsonResult verifyTicket(String ticket);
}
