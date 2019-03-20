package com.fyp.fly.web.controller.biz;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.web.client.article.ArticleApiClient;
import com.fyp.fly.web.client.comment.CommentApiClient;
import com.fyp.fly.web.controller.form.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleApiClient articleApiClient;

    @Autowired
    private CommentApiClient commentApiClient;

    @PostMapping("/browse/{id}")
    public JsonResult browse(@PathVariable("id") Long id) {
        return articleApiClient.browse(id);
    }

    @PostMapping("/remove/{id}")
    public JsonResult remove(@PathVariable("id") Long id) {
        return articleApiClient.remove(id);
    }

    @PostMapping("/reply")
    public JsonResult reply(CommentDto comment) {
        return commentApiClient.add(comment);
    }

    @PostMapping("/reply/remove/{id}")
    public JsonResult removeReply(@PathVariable("id") Long id) {
        return commentApiClient.remove(id);
    }
}
