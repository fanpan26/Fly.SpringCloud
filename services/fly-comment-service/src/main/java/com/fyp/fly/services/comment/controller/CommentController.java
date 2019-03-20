package com.fyp.fly.services.comment.controller;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.comment.domain.CommentDto;
import com.fyp.fly.services.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
