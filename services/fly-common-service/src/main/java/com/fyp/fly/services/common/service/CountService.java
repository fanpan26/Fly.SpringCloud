package com.fyp.fly.services.common.service;

import com.fyp.fly.common.dto.CountDto;
import com.fyp.fly.common.result.api.JsonResult;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/3/17 10:03
 * @project fly
 */
public interface CountService {
    JsonResult increment(int type, Long bizId);

    JsonResult decrement(int type, Long bizId);

    JsonResult<List<CountDto>> getListByBizIds(int bizType, List<Long> bizIds);

    JsonResult<List<CountDto>> getListByBizTypes(Long bizId,List<Integer> bizTypes);
}
