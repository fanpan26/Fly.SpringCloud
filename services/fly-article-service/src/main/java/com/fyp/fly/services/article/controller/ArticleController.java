package com.fyp.fly.services.article.controller;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.article.dto.ArticleEditDto;
import com.fyp.fly.services.article.service.ArticleService;
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

    /**
     * 添加一个帖子
     * */
    @PostMapping("/")
    public JsonResult add(ArticleEditDto article) {
        return articleService.add(article);
    }
}
