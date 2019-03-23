package com.fyp.fly.web.client.comment;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.web.controller.form.CommentDto;

public interface CommentApiClient {
    JsonResult add(CommentDto comment);
    JsonResult remove(Long id);
    JsonResult getContent(Long id);
    JsonResult updateContent(Long id, String content);
    JsonResult adopt(Long id, Long artId);
    JsonResult getList(Long artId,Long authorId,Integer pageIndex,Integer pageSize);
}
