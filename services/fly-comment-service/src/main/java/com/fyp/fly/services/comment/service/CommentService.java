package com.fyp.fly.services.comment.service;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.comment.domain.CommentDto;
import com.fyp.fly.services.comment.domain.CommentListDto;

public interface CommentService {
    JsonResult add(CommentDto comment);
    JsonResult delete(Long id,Long userId);
    JsonResult getContent(Long id);
    JsonResult updateContent(Long id,Long uid,String content);
    JsonResult getList(CommentListDto listParam);
    JsonResult adopt(Long id,Long artId);
}
