package com.fyp.fly.services.comment.domain.dto;

import com.fyp.fly.common.dto.BaseDto;
import com.fyp.fly.services.comment.domain.Comment;
import org.apache.commons.lang.StringUtils;

public class CommentDto extends BaseDto {
    private Long artId;
    private Long userId;

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    private Long replyId;

    public Long getArtId() {
        return artId;
    }

    public void setArtId(Long artId) {
        this.artId = artId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    @Override
    public String checkArguments() {
        if (StringUtils.isEmpty(content)){
            return "评论内容不可为空";
        }
        if (userId == null || userId <= 0){
            return "无效的用户";
        }
        if (artId == null || artId <= 0){
            return "无效的帖子";
        }
        return null;
    }

    public Comment transfer(){
        return new Comment(artId,content,userId,replyId);
    }
}
