package com.fyp.fly.services.comment.domain;

import com.fyp.fly.common.utils.IdUtils;

public class Comment {

    public Comment(Long artId,String content,Long userId,Long replyId) {
        createAt = System.currentTimeMillis();
        id = IdUtils.next(userId);
        this.artId = artId;
        this.content = content;
        this.uid = userId;
        this.replyId = replyId;
        this.adopt = false;
    }

    private long id;
    private long uid;
    private long artId;
    private String content;
    private long createAt;
    private boolean adopt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getArtId() {
        return artId;
    }

    public void setArtId(long artId) {
        this.artId = artId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public boolean isAdopt() {
        return adopt;
    }

    public void setAdopt(boolean adopt) {
        this.adopt = adopt;
    }

    public long getReplyId() {
        return replyId;
    }

    public void setReplyId(long replyId) {
        this.replyId = replyId;
    }

    private long replyId;
}
