package com.fyp.fly.services.article.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fyp
 * @crate 2019/3/1 22:58
 * @project fly
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/{id}")
    public String getArticleById(@PathVariable Long id){
        return "this is from article "+ id;
    }
}
