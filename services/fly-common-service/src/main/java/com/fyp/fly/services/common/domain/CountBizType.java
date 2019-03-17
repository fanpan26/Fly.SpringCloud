package com.fyp.fly.services.common.domain;

/**
 * @author fyp
 * @crate 2019/3/17 10:30
 * @project fly
 */
public enum CountBizType {
    /**
     * 帖子浏览量
     * */
    ARTICLE_BROWSER(1),
    /**
     * 帖子评论量
     * */
    ARTICLE__COMMENT(2),
    /**
     * 评论点赞数
     * */
    COMMENT_LIKE(3),
    /**
     * 用户关注量
     * */
    USER_FOLLOW(4);

    CountBizType(int code) {
        this.code = code;
    }

    private int code;
    public int getCode(){
        return code;
    }

    public static CountBizType valueOf(int code){
        for (CountBizType e : CountBizType.values()){
            if (e.getCode() == code){
                return e;
            }
        }
        throw new IllegalArgumentException("bizType with code "+ code +" does not exist");
    }
}
