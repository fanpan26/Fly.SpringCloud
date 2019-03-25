package com.fyp.fly.services.article.service;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.article.domain.dto.ArticleEditDto;

/**
 * @author fyp
 * @crate 2019/3/16 7:48
 * @project fly
 */
public interface ArticleService {
    JsonResult add(ArticleEditDto article);

    JsonResult findOneById(Long id);

    JsonResult browse(Long id);

    JsonResult delete(Long id);

    /**
     * 获取TOP 10 热议的文章列表
     * */
    JsonResult getTopNCommentList(int top);

    /**
     * 获取用户最近发布列表
     * */
    JsonResult getRecentPublishedByUserId(Long userId);
}
