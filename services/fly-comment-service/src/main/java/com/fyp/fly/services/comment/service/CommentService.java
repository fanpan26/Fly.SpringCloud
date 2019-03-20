package com.fyp.fly.services.comment.service;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.comment.domain.CommentDto;

public interface CommentService {
    JsonResult add(CommentDto comment);
    JsonResult addCache(CommentDto comment);
    JsonResult delete(Long id);
    JsonResult getContent(Long id);
    JsonResult updateContent(Long id,Long uid,String content);
}
