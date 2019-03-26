package com.fyp.fly.services.comment.domain.vo;

import java.io.Serializable;

/**
 * @author fyp
 * @crate 2019/3/26 21:28
 * @project fly
 */
public class UserTopCommentVo implements Serializable{
    private Long id;
    private String avatar;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    private Integer count;
}
