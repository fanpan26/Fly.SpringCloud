package com.fyp.fly.gateway.controller;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.gateway.service.ArticleAggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fyp
 * @crate 2019/3/17 22:08
 * @project fly
 */
@RestController
@RequestMapping("/aggregation")
public class ArticleController {


    @Autowired
    private ArticleAggregationService articleAggregationService;

    @GetMapping("/article/{id}")
    public JsonResult getArticleById(@PathVariable("id") Long id, Long userId) {
        return articleAggregationService.getArticle(id, userId);
    }
}
