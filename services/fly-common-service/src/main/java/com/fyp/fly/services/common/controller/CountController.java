package com.fyp.fly.services.common.controller;

import com.fyp.fly.common.dto.CountDto;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.common.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fyp
 * @crate 2019/3/17 11:00
 * @project fly
 */
@RestController
@RequestMapping("/count")
public class CountController {

    @Autowired
    private CountService countService;

    /**
     * 添加计数
     * */
    @PostMapping("/{bizType}/{bizId}")
    public JsonResult getCountByBizId(@PathVariable("bizType") int type,@PathVariable("bizId") Long bizId) {
        return countService.add(type, bizId);
    }

    /**
     * 获取计数详情
     * */
    @PostMapping("/type/{bizType}")
    public JsonResult getCountsByBizIds(@PathVariable("bizType") int type,@RequestBody List<Long> bizIds) {
        return countService.getListByBizIds(type, bizIds);
    }

    /**
     * 获取某个计数详情
     * */
    @PostMapping("/id/{bizId}")
    public JsonResult getCountsByBizTypes(@PathVariable("bizId") Long bizId,@RequestBody List<Integer> bizTypes) {
        return countService.getListByBizTypes(bizId, bizTypes);
    }
}
