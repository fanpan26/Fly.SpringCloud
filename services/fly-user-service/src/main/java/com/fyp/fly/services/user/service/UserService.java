package com.fyp.fly.services.user.service;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.user.domain.User;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/3/13 20:10
 * @project fly
 */
public interface UserService {

    /**
     * 获取用户信息
     * */
    JsonResult getUserInfo(Long userId);

    JsonResult getUserList(List<String> userIds);

}
