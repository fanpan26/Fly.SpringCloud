package com.fyp.fly.services.comment.client;

import com.fyp.fly.common.dto.CountVo;
import com.fyp.fly.common.result.api.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/3/26 21:23
 * @project fly
 */
@FeignClient(name = "fly-common-service")
public interface CountFeignClient {
    /**
     * 获取TOP N 的数据统计
     * */
    @RequestMapping(value = "/count/top/{bizType}",method = RequestMethod.GET)
    JsonResult<List<CountVo>> getTopNCountsByBizType(@PathVariable("bizType") Integer bizType, @RequestParam("start") Integer start, @RequestParam("end") Integer end);

    /**
     * 根据ID列表获取对应的个数
     * */
    @RequestMapping(value = "/count/type/{bizType}",method = RequestMethod.GET)
    JsonResult<List<CountVo>> getCountsByBizIds(@PathVariable("bizType") int type,@RequestBody List<Long> bizIds);
}