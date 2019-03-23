package com.fyp.fly.services.comment.domain;

import com.fyp.fly.common.dto.UserModel;
import com.fyp.fly.common.utils.DateUtils;
import com.fyp.fly.common.utils.IdUtils;

import java.io.Serializable;

public class Comment implements Serializable{

    public Comment(){}
    public Comment(Long artId,String content,Long userId,Long replyId) {
        createAt = System.currentTimeMillis();
        id = IdUtils.nextId(1,userId);
        this.artId = artId;
        this.content = content;
        this.uid = userId;
        this.replyId = replyId;
        this.adopt = false;
    }

    private long id;
    private long uid;
    private transient long artId;
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

    public String getCreateAtStr(){
        return DateUtils.offset(createAt);
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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    private UserModel user;

    public boolean isAuthor() {
        return author;
    }

    public void setAuthor(boolean author) {
        this.author = author;
    }

    public boolean isEditor() {
        return editor;
    }

    public void setEditor(boolean editor) {
        this.editor = editor;
    }


    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    private boolean mine;
    private boolean author;
    private boolean editor;

}
