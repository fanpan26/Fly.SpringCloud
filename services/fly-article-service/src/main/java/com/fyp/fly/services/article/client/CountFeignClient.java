package com.fyp.fly.services.article.client;

import com.fyp.fly.common.dto.CountVo;
import com.fyp.fly.common.result.api.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/3/24 14:51
 * @project fly
 */
@FeignClient(name = "fly-common-service")
public interface CountFeignClient {
    /**
     * 获取TOP N 的数据统计
     * */
    @RequestMapping(value = "/count/top/{bizType}",method = RequestMethod.GET)
    JsonResult<List<CountVo>> getTopNCountsByBizType(@PathVariable("bizType") Integer bizType, Integer start, Integer end);
}
