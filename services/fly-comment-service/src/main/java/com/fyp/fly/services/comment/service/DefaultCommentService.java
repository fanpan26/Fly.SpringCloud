package com.fyp.fly.services.comment.service;

import com.fyp.fly.common.enums.CountBizType;
import com.fyp.fly.common.event.CountEvent;
import com.fyp.fly.common.event.FlyEvent;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.common.utils.JSONUtils;
import com.fyp.fly.services.comment.domain.Comment;
import com.fyp.fly.services.comment.domain.CommentDto;
import com.fyp.fly.services.comment.repository.mapper.CommentMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultCommentService implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public JsonResult add(CommentDto comment) {
        if (comment == null) {
            throw new NullPointerException("comment");
        }
        if (comment.isValid()) {
            Comment commentModel = comment.transfer();
            commentMapper.add(commentModel);
            sendCommentCountChangedEvent(comment.getArtId(),true);
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
        Long artId = commentMapper.getArtIdById(id);
        if (artId == null){
            return ResultUtils.failed("帖子不存在");
        }
        commentMapper.delete(id);
        sendCommentCountChangedEvent(artId,false);
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

    private void sendCommentCountChangedEvent(Long id,boolean increment) {

        CountEvent event = new CountEvent();
        event.setBizId(id);
        event.setBizType(CountBizType.ARTICLE_COMMENT.getCode());
        event.setIncrement(increment);

        rabbitTemplate.convertAndSend(FlyEvent.SERVICE_COMMON_EXCHANGE, FlyEvent.SERVICE_ARTICLE_COUNT_EVENT, JSONUtils.toJSONString(event));
    }
}
