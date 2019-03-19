package com.fyp.fly.services.article.service;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.services.article.dto.ArticleEditDto;

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
}
