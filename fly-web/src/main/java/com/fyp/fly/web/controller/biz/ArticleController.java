package com.fyp.fly.web.controller.biz;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.web.client.article.ArticleApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleApiClient articleApiClient;

    @PostMapping("/browse/{id}")
    public JsonResult browse(@PathVariable("id") Long id) {
        return articleApiClient.browse(id);
    }

    @PostMapping("/remove/{id}")
    public JsonResult remove(@PathVariable("id") Long id) {
        return articleApiClient.remove(id);
    }
}
