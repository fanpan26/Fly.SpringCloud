package com.fyp.fly.services.common.service;

import com.fyp.fly.common.dto.CountVo;
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

    JsonResult<List<CountVo>> getListByBizIds(int bizType, List<Long> bizIds);

    JsonResult<List<CountVo>> getListByBizTypes(Long bizId, List<Integer> bizTypes);

    JsonResult<List<CountVo>> getSortedListByBizType(Integer bizType,Integer start,Integer end);


}
