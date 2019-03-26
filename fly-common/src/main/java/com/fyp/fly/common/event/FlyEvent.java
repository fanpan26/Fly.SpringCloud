package com.fyp.fly.common.event;

public class FlyEvent {
    public static final String SERVICE_COUNT_TOPIC = "fly.topic.count.*";
    public static final String SERVICE_COMMON_EXCHANGE = "service-common-exchange";

    /**
     * 帖子数变化TOPIC
     * */
    public static final String SERVICE_ARTICLE_COUNT_EVENT = "fly.topic.count.article";
    public static final String SERVICE_ARTICLE_COMMENT_COUNT_EVENT = "fly.topic.count.comment-article";
    public static final String SERVICE_USER_COMMENT_COUNT_EVENT = "fly.topic.count.comment-user";
}
