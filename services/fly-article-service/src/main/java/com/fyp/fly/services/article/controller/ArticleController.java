package com.fyp.fly.services.article.controller;

import com.fyp.fly.common.enums.CountBizType;
import com.fyp.fly.common.event.CountEvent;
import com.fyp.fly.common.event.FlyEvent;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.services.article.dto.ArticleEditDto;
import com.fyp.fly.services.article.service.ArticleService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fyp
 * @crate 2019/3/1 22:58
 * @project fly
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 添加一个帖子
     * */
    @PostMapping("/")
    public JsonResult add(ArticleEditDto article) {
        return articleService.add(article);
    }

    @GetMapping("/{id}")
    public JsonResult findOneById(@PathVariable("id") Long id){
        return articleService.findOneById(id);
    }

    /**
     * 帖子浏览
     * */
    @PostMapping("/browse/{id}")
    public JsonResult browse(@PathVariable("id") Long id) {
       return articleService.browse(id);
    }
}
