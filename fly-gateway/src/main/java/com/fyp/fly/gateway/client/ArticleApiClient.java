package com.fyp.fly.gateway.client;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.gateway.client.domain.Article;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author fyp
 * @crate 2019/3/17 21:21
 * @project fly
 */
@FeignClient(name = "fly-article-service")
public interface ArticleApiClient {
    /**
     * 获取帖子信息
     * */
    @RequestMapping(value = "/article/{id}",method = RequestMethod.GET)
    JsonResult<Article> getArticleById(@PathVariable("id") Long id);
}
