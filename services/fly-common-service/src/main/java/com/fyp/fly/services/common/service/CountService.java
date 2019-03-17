package com.fyp.fly.services.common.service;

import com.fyp.fly.common.dto.CountDto;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.common.domain.Count;
import com.fyp.fly.services.common.domain.CountBizType;

import java.util.List;

/**
 * @author fyp
 * @crate 2019/3/17 10:03
 * @project fly
 */
public interface CountService {
    JsonResult add(int type, Long bizId);

    JsonResult<List<CountDto>> getListByBizIds(int bizType, long... bizIds);
}
