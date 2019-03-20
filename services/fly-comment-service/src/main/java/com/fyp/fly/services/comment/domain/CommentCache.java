package com.fyp.fly.services.comment.domain;

import java.io.Serializable;

/**
 * @author fyp
 * @crate 2019/3/20 20:47
 * @project fly
 */
public class CommentCache implements Serializable {
    private Long id;
    private Long uid;
    private boolean editor;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public boolean isEditor() {
        return editor;
    }

    public void setEditor(boolean editor) {
        this.editor = editor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    private Long createAt;
}
