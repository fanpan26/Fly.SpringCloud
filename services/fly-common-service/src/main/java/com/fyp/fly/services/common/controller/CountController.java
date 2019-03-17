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

    @GetMapping("/{bizType}/{bizId}")
    public JsonResult getCountByBizId(@PathVariable("bizType") int type,@PathVariable("bizId") Long bizId) {
        return countService.add(type, bizId);
    }

    @PostMapping("/{bizType}")
    public JsonResult getCountsByBizIds(@PathVariable("bizType") int type,@RequestBody List<Long> bizIds) {
        return countService.getListByBizIds(type, bizIds);
    }

    @GetMapping("/test")
    public JsonResult getCountsByBizIdsTest() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        ids.add(4L);
        ids.add(100001L);
        return countService.getListByBizIds(1, ids);
    }
}
