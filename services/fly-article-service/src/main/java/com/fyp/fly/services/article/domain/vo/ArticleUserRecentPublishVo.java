package com.fyp.fly.services.article.domain.vo;

import com.fyp.fly.common.utils.DateUtils;

import java.io.Serializable;

/**
 * 用户最近发布
 * */
public class ArticleUserRecentPublishVo implements Serializable {
    private long id;
    private boolean special;
    private boolean top;
    private String title;
    private String createAt;
    private int browseCount;
    private int commentCount;

    public ArticleUserRecentPublishVo(long id,boolean special,boolean top,String title,long createAtLong,int browseCount,int commentCount) {
        this.id = id;
        this.special = special;
        this.top = top;
        this.title = title;
        this.createAt = DateUtils.offset(createAtLong);
        this.browseCount = browseCount;
        this.commentCount = commentCount;
    }

    public long getId() {
        return id;
    }

    public boolean isSpecial() {
        return special;
    }

    public boolean isTop() {
        return top;
    }

    public String getTitle() {
        return title;
    }

    public String getCreateAt() {
        return createAt;
    }

    public int getBrowseCount() {
        return browseCount;
    }

    public int getCommentCount() {
        return commentCount;
    }


}
