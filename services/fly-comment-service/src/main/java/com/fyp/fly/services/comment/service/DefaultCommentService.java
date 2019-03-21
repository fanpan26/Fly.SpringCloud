package com.fyp.fly.services.comment.service;

import com.fyp.fly.common.dto.UserModel;
import com.fyp.fly.common.enums.CountBizType;
import com.fyp.fly.common.event.CountEvent;
import com.fyp.fly.common.event.FlyEvent;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.common.utils.JSONUtils;
import com.fyp.fly.services.comment.client.UserFeignClient;
import com.fyp.fly.services.comment.domain.Comment;
import com.fyp.fly.services.comment.domain.CommentCache;
import com.fyp.fly.services.comment.domain.CommentDto;
import com.fyp.fly.services.comment.domain.CommentListDto;
import com.fyp.fly.services.comment.repository.mapper.CommentMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DefaultCommentService implements CommentService {

    private static final String CACHE_COMMENT_LIST = "service:comment:l_";

    @Autowired
    private ListOperations<String, Object> listOps;


    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public JsonResult add(CommentDto comment) {
        if (comment == null) {
            throw new NullPointerException("comment");
        }
        if (comment.isValid()) {
            Comment commentModel = comment.transfer();
            commentMapper.add(commentModel);
            listOps.rightPush(CACHE_COMMENT_LIST + comment.getArtId(), commentModel);
            sendCommentCountChangedEvent(comment.getArtId(), true);
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

    @Override
    public JsonResult getList(CommentListDto listParam) {
        List<Object> commentList = listOps.range(CACHE_COMMENT_LIST + listParam.getArtId(), listParam.getStart(), listParam.getEnd());

        if (commentList.isEmpty()){
            return ResultUtils.success(new ArrayList<Comment>(0));
        }
        List<Comment> comments = commentList.stream().map(c -> JSONUtils.parseObject(c.toString(), Comment.class)).collect(Collectors.toList());
        List<Long> userIds = comments.stream().map(x -> x.getUid()).distinct().collect(Collectors.toList());
        //根据用户ID获取用户集合
        JsonResult<List<UserModel>> userList = userFeignClient.getList(userIds);
        if (ResultUtils.isSuccess(userList)) {
            comments.stream().forEach(c ->{
                c.setUser(userList.getData().stream().filter(x -> c.getUid() == x.getId()).findFirst().get());
                // if user is article author
                c.setAuthor(Objects.equals(c.getUid(),listParam.getAuthorId()));
                // if user is comment editor
                c.setEditor(Objects.equals(c.getUid(),listParam.getUserId()));
            });
        }
        return ResultUtils.success(comments);
    }

    private void sendCommentCountChangedEvent(Long id,boolean increment) {

        CountEvent event = new CountEvent();
        event.setBizId(id);
        event.setBizType(CountBizType.ARTICLE_COMMENT.getCode());
        event.setIncrement(increment);

        rabbitTemplate.convertAndSend(FlyEvent.SERVICE_COMMON_EXCHANGE, FlyEvent.SERVICE_ARTICLE_COUNT_EVENT, JSONUtils.toJSONString(event));
    }
}
