package com.fyp.fly.gateway.client.domain;

import java.util.HashMap;

public class ArticleCategory {

    private static final java.util.Map<Integer,String> ARTICLE_CATEGORY = new HashMap<>(5);
    static{
        ARTICLE_CATEGORY.put(0,"提问");
        ARTICLE_CATEGORY.put(99,"分享");
        ARTICLE_CATEGORY.put(100,"讨论");
        ARTICLE_CATEGORY.put(101,"建议");
        ARTICLE_CATEGORY.put(168,"公告");
        ARTICLE_CATEGORY.put(169,"动态");
    }

    public static String nameOfCategory(Integer category){
        return ARTICLE_CATEGORY.get(category);
    }
}
