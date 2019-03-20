package com.fyp.fly.services.comment.controller;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.comment.domain.CommentDto;
import com.fyp.fly.services.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
public class CommentController {


    @Autowired
    private CommentService commentService;

    @PostMapping("/")
    public JsonResult add(CommentDto comment) {
        return commentService.add(comment);
    }

    @PostMapping("/remove/{id}")
    public JsonResult remove(@PathVariable("id") Long id) {
        return commentService.delete(id);
    }

    @GetMapping("/content/{id}")
    public JsonResult getContent(@PathVariable("id") Long id) {
        return commentService.getContent(id);
    }

    @PostMapping("/content/{id}")
    public JsonResult updateContent(@PathVariable("id") Long id,Long userId,String content){
        return commentService.updateContent(id,userId,content);
    }
}
