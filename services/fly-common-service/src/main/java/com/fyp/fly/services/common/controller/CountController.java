package com.fyp.fly.services.common.controller;

import com.fyp.fly.common.dto.CountDto;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.common.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public CountDto getCountByBizId(@PathVariable("bizType") int type,@PathVariable("bizId") Long bizId){
        countService.add(type,bizId);
        return new CountDto();
    }
}
