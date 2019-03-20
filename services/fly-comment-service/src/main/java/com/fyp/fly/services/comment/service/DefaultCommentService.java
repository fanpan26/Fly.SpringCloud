package com.fyp.fly.services.comment.service;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.services.comment.domain.Comment;
import com.fyp.fly.services.comment.domain.CommentDto;
import com.fyp.fly.services.comment.repository.mapper.CommentMapper;
import org.apache.commons.lang.StringUtils;
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
            Comment commentModel = comment.transfer();
            commentMapper.add(commentModel);
            return ResultUtils.success(commentModel.getId());
        } else {
            return ResultUtils.failed(comment.getErrMsg());
        }
    }

    @Override
    public JsonResult delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id");
        }
        commentMapper.delete(id);
        return ResultUtils.success();
    }

    @Override
    public JsonResult getContent(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id");
        }
        String content = commentMapper.getContentById(id);
        return ResultUtils.stringResult(content);
    }

    @Override
    public JsonResult updateContent(Long id, Long uid, String content) {
        if (id == null || uid == null || StringUtils.isEmpty(content)){
            throw new IllegalArgumentException("id/uid/content");
        }
        commentMapper.updateContent(id,uid,content);
        return ResultUtils.success();
    }
}
