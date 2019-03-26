package com.fyp.fly.services.comment.service;

import com.fyp.fly.common.dto.CountVo;
import com.fyp.fly.common.dto.UserModel;
import com.fyp.fly.common.enums.CountBizType;
import com.fyp.fly.common.event.CountEvent;
import com.fyp.fly.common.event.FlyEvent;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.common.utils.JSONUtils;
import com.fyp.fly.services.comment.client.CountFeignClient;
import com.fyp.fly.services.comment.client.UserFeignClient;
import com.fyp.fly.services.comment.domain.Comment;
import com.fyp.fly.services.comment.domain.dto.CommentDto;
import com.fyp.fly.services.comment.domain.dto.CommentListDto;
import com.fyp.fly.services.comment.domain.vo.UserTopCommentVo;
import com.fyp.fly.services.comment.repository.mapper.CommentMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultCommentService implements CommentService {

    private static final String CACHE_COMMENT_LIST = "service:comment:l_";
    private static final String CACHE_COMMENT_ARTICLE_ADOPT = "service:comment:adopt";

    @Autowired
    private ListOperations<String, Object> listOps;

    @Autowired
    private HashOperations<String,String,Object> hashOps;


    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private CountFeignClient countFeignClient;

    @Override
    public JsonResult add(CommentDto comment) {
        if (comment.isValid()) {
            Comment commentModel = comment.transfer();
            commentMapper.add(commentModel);
            listOps.rightPush(CACHE_COMMENT_LIST + comment.getArtId(), commentModel);
            sendCommentCountChangedEvent(comment.getArtId(), true,CountBizType.ARTICLE_COMMENT);
            sendCommentCountChangedEvent(comment.getUserId(), true,CountBizType.USER_COMMENT);
            return ResultUtils.success(commentModel.getId());
        } else {
            return ResultUtils.failed(comment.getErrMsg());
        }
    }

    @Override
    public JsonResult delete(Long id,Long userId) {
        Long artId = commentMapper.getArtIdById(id);
        if (artId == null) {
            return ResultUtils.failed("帖子不存在");
        }
        commentMapper.delete(id);
        sendCommentCountChangedEvent(artId, false, CountBizType.ARTICLE_COMMENT);
        sendCommentCountChangedEvent(userId, false, CountBizType.USER_COMMENT);
        return ResultUtils.success();
    }
    @Override
    public JsonResult getContent(Long id) {
        String content = commentMapper.getContentById(id);
        return ResultUtils.stringResult(content);
    }

    @Override
    public JsonResult updateContent(Long id, Long uid, String content) {
        commentMapper.updateContent(id,uid,content);
        return ResultUtils.success();
    }

    @Override
    public JsonResult getList(CommentListDto listParam) {
        List<Object> commentList = listOps.range(CACHE_COMMENT_LIST + listParam.getArtId(), listParam.getStart(), listParam.getEnd());

        if (commentList.isEmpty()) {
            return ResultUtils.success(new ArrayList<Comment>(0));
        }
        List<Comment> comments = commentList.stream().map(c -> JSONUtils.parseObject(c.toString(), Comment.class)).collect(Collectors.toList());
        List<Long> userIds = comments.stream().map(x -> x.getUid()).distinct().collect(Collectors.toList());
        //根据用户ID获取用户集合
        JsonResult<List<UserModel>> userList = userFeignClient.getList(userIds);
        Long adoptId = getAdoptIdByArtId(listParam.getArtId());
        boolean isMine = listParam.isMine();
        if (ResultUtils.isSuccess(userList)) {
            comments.stream().forEach(c -> {
                c.setUser(userList.getData().stream().filter(x -> c.getUid() == x.getId()).findFirst().get());
                // if user is article author
                c.setAuthor(Objects.equals(c.getUid(), listParam.getAuthorId()));
                // if user is comment editor
                c.setEditor(Objects.equals(c.getUid(), listParam.getUserId()));
                c.setMine(isMine);
                c.setArtAdopted(adoptId > 0);
                c.setAdopt(adoptId > 0 ? Objects.equals(c.getId(), adoptId) : false);
            });
        }
        return ResultUtils.success(comments);
    }

    @Override
    public JsonResult adopt(Long id,Long artId) {
        String artIdStr = artId.toString();
        Long adoptId = getAdoptIdByArtId(artId);

        if (adoptId != 0) {
            if (Objects.equals(id, adoptId)) {
                return ResultUtils.success(id);
            } else {
                return ResultUtils.failed("已经有其他答案被采纳");
            }
        }
        commentMapper.adopt(id);
        //add cache <hashKey>  artId, <hashValue> adopt commentId
        hashOps.put(CACHE_COMMENT_ARTICLE_ADOPT, artIdStr, id.toString());
        return ResultUtils.success(id);
    }

    @Override
    public JsonResult getTopNCommentUserList() {
        List<UserTopCommentVo> resultList = new ArrayList<>();

        JsonResult<List<CountVo>> countRes = countFeignClient.getTopNCountsByBizType(CountBizType.USER_COMMENT.getCode(), 0, 12);
        if (ResultUtils.isSuccess(countRes)) {
            List<CountVo> counts = countRes.getData();
            List<Long> userIds = counts.stream().map(x -> x.getBizId()).collect(Collectors.toList());
            JsonResult<List<UserModel>> userRes = userFeignClient.getList(userIds);

            if (ResultUtils.isSuccess(userRes)) {
                List<UserModel> users = userRes.getData();
                counts.stream().forEach(x -> {
                    Optional<UserModel> currentUser = users.stream().filter(u -> Objects.equals(u.getId(), x.getBizId())).findFirst();
                    if (currentUser.isPresent()) {
                        UserTopCommentVo vo = new UserTopCommentVo();
                        vo.setCount(x.getBizCount());
                        vo.setId(x.getBizId());
                        vo.setName(currentUser.get().getName());
                        vo.setAvatar(currentUser.get().getAvatar());
                        resultList.add(vo);
                    }
                });
            }
        }
        return ResultUtils.success(resultList);
    }

    private void sendCommentCountChangedEvent(Long id,boolean increment,CountBizType bizType) {

        CountEvent event = new CountEvent();
        event.setBizId(id);
        event.setBizType(bizType.getCode());
        event.setIncrement(increment);

        rabbitTemplate.convertAndSend(FlyEvent.SERVICE_COMMON_EXCHANGE, FlyEvent.SERVICE_ARTICLE_COMMENT_COUNT_EVENT, JSONUtils.toJSONString(event));
    }


    private Long getAdoptIdByArtId(Long artId){
        Object  adoptId = hashOps.get(CACHE_COMMENT_ARTICLE_ADOPT,artId.toString());
        if (adoptId == null){
            return 0L;
        }
        return Long.valueOf(adoptId.toString());
    }
}
