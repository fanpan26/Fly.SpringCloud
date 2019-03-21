package com.fyp.fly.services.comment.client;

import com.fyp.fly.common.dto.UserModel;
import com.fyp.fly.common.result.api.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/3/21 21:20
 * @project fly
 */
@FeignClient(name = "fly-user-service")
public interface UserFeignClient {
    /**
     * 获取用户信息
     * */
    @RequestMapping(value = "/user/list",method = RequestMethod.POST)
    JsonResult<List<UserModel>> getList(@RequestParam("userIds") List<Long> userIds);
}