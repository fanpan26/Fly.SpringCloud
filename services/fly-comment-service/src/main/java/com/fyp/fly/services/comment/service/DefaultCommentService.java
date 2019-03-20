package com.fyp.fly.services.comment.service;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.services.comment.domain.CommentDto;
import com.fyp.fly.services.comment.repository.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultCommentService implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public JsonResult add(CommentDto comment) {
        if (comment == null) {
            throw new NullPointerException("comment");
        }
        if (comment.isValid()) {
            commentMapper.add(comment.transfer());
            return ResultUtils.success();
        } else {
            return ResultUtils.failed(comment.getErrMsg());
        }
    }
}
