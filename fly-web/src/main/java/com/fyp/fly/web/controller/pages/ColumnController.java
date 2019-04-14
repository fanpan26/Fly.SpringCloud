package com.fyp.fly.web.controller.pages;

import com.fyp.fly.web.controller.biz.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

/**
 * @author fyp
 * @crate 2019/4/14 20:24
 * @project fly
 */
@Controller
@RequestMapping("/column")
public class ColumnController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ColumnController.class);
    /**
     * 帖子首页
     */
    @GetMapping("/")
    public String index() {
        return indexByType("question");
    }
    @GetMapping("/{type}")
    public String indexByType(@PathVariable("type") String type) {
        return indexByTypeAndStatus(type,"all");
    }

    @GetMapping("/{type}/{status}")
    public String indexByTypeAndStatus(@PathVariable("type") String type,@PathVariable("status") String status) {
        logger.info("type:{},status:{}", type, status);
        return "/jie/index";
    }
}
