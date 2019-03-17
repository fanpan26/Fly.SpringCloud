package com.fyp.fly.gateway.client;

import com.fyp.fly.common.dto.CountDto;
import com.fyp.fly.common.result.api.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/3/17 21:50
 * @project fly
 */
@FeignClient(name = "fly-common-service")
public interface CommonApiClient {

    /**
     * 根据业务ID获取与该业务ID相关的统计数据
     * */
    @PostMapping("/count/id/{bizId}")
    JsonResult<List<CountDto>> getCountsByBizTypes(@PathVariable("bizId") Long bizId, @RequestBody List<Integer> bizTypes);
}
