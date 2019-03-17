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
    ARTICLE_BROWSER(1,"ab"),
    /**
     * 帖子评论量
     * */
    ARTICLE__COMMENT(2,"ac"),
    /**
     * 评论点赞数
     * */
    COMMENT_LIKE(3,"cl"),
    /**
     * 用户关注量
     * */
    USER_FOLLOW(4,"uf");

    CountBizType(int code,String key) {
        this.code = code;
        this.key = key;
    }

    private String key;
    private int code;
    public int getCode(){
        return code;
    }

    public String getKey() {
        return key;
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
