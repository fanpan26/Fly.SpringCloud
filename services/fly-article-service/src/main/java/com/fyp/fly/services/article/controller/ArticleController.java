package com.fyp.fly.services.article.controller;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.article.domain.dto.ArticleEditDto;
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

    /**
     * 帖子删除
     * */
    @PostMapping("/remove/{id}")
    public JsonResult remove(@PathVariable("id") Long id) {
        return articleService.delete(id);
    }

    @GetMapping("/top/{top}")
    public JsonResult getTopN(@PathVariable("top") Integer top) {
        return articleService.getTopNCommentList(top);
    }

    @GetMapping("/recent/{userId}")
    public JsonResult getRecentPublishedByUserId(@PathVariable("userId") Long userId) {
        return articleService.getRecentPublishedByUserId(userId);
    }
}
