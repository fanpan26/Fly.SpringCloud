package com.fyp.fly.gateway.client;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.gateway.client.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author fyp
 * @crate 2019/3/17 21:23
 * @project fly
 */
@FeignClient(name = "fly-user-service")
public interface UserApiClient {
    /**
     * 获取用户信息
     * */
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    JsonResult<User> getUserById(@PathVariable("id") Long id);
}
