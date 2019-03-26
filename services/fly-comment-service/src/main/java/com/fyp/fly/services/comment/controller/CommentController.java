package com.fyp.fly.services.comment.controller;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.comment.domain.CommentDto;
import com.fyp.fly.services.comment.domain.CommentListDto;
import com.fyp.fly.services.comment.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "用户账户接口")
@RestController
@RequestMapping("comment")
public class CommentController {


    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "添加评论")
    @PostMapping("/")
    public JsonResult add(CommentDto comment) {
        return commentService.add(comment);
    }

    @ApiOperation(value = "删除评论")
    @PostMapping("/remove/{id}")
    public JsonResult remove(@PathVariable("id") Long id,Long userId) {
        return commentService.delete(id,userId);
    }

    @ApiOperation(value = "获取评论内容")
    @GetMapping("/content/{id}")
    public JsonResult getContent(@PathVariable("id") Long id) {
        return commentService.getContent(id);
    }

    @ApiOperation(value = "更新评论内容")
    @PostMapping("/content/{id}")
    public JsonResult updateContent(@PathVariable("id") Long id,Long userId,String content){
        return commentService.updateContent(id,userId,content);
    }

    @ApiOperation(value = "获取评论列表")
    @GetMapping("/list")
    public JsonResult getList(CommentListDto listParam) {
        return commentService.getList(listParam);
    }

    @ApiOperation(value = "采纳答案")
    @PostMapping("/adopt/{id}")
    public JsonResult adopt(@PathVariable("id") Long id,Long artId) {
        return commentService.adopt(id,artId);
    }
}
