package com.fyp.fly.services.user.services;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.user.domain.dto.FlyUserBaseInfoDto;

/**
 * @author fyp
 * @crate 2019/3/13 20:10
 * @project fly
 */
public interface UserService {
    /**
     * 获取用户最基础的信息
     * */
    JsonResult<FlyUserBaseInfoDto> getUserBaseInfo(Long userId);
}
