package com.fyp.fly.web.clients.user;

import com.fyp.fly.common.dto.FlyUserDto;
import com.fyp.fly.common.result.api.JsonResult;

/**
 * @author fyp
 * @crate 2019/3/13 21:57
 * @project fly
 */
public interface UserApiClient {
    JsonResult<FlyUserDto> getUserById(Long userId);
}
