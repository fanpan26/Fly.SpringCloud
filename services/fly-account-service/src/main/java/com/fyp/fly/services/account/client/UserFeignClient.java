package com.fyp.fly.services.account.client;

import com.fyp.fly.common.dto.UserModel;
import com.fyp.fly.common.result.api.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author fyp
 * @crate 2019/3/10 23:45
 * @project fly
 */
@FeignClient(name = "fly-user-service")
public interface UserFeignClient {
    /**
     * 获取用户信息
     * */
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    JsonResult<UserModel> getUserById(@PathVariable("id") Long id);
}
