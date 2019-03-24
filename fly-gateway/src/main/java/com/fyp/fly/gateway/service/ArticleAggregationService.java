package com.fyp.fly.gateway.service;

import com.fyp.fly.common.dto.CountVo;
import com.fyp.fly.common.enums.CountBizType;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.gateway.client.ArticleApiClient;
import com.fyp.fly.gateway.client.CommonApiClient;
import com.fyp.fly.gateway.client.UserApiClient;
import com.fyp.fly.gateway.client.domain.Article;
import com.fyp.fly.gateway.client.domain.User;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 帖子聚合服务，fly-article-service, fly-user-service, fly-common-service
 * @author fyp
 * @crate 2019/3/17 21:31
 * @project fly
 */
@Service
public class ArticleAggregationService {
    @Autowired
    private ArticleApiClient articleApiClient;
    @Autowired
    private UserApiClient userApiClient;
    @Autowired
    private CommonApiClient commonApiClient;

    private static final List<Integer> ARTICLE_COUNT_BIZ_TYPE ;
    static {
        ARTICLE_COUNT_BIZ_TYPE = new ArrayList<>(2);
        //对应fly-common-service CountBizType 1 浏览量 2 评论量
        ARTICLE_COUNT_BIZ_TYPE.add(CountBizType.ARTICLE_BROWSER.getCode());
        ARTICLE_COUNT_BIZ_TYPE.add(CountBizType.ARTICLE_COMMENT.getCode());

    }

   public JsonResult getArticle(Long articleId,Long userId){
       JsonResult<Article> articleRes = articleApiClient.getArticleById(articleId);
       if (ResultUtils.isSuccess(articleRes)) {
           Article article = articleRes.getData();
           Long authorId = article.getAuthor();
           //判断当前请求的用户是否为作者
           boolean isAuthor = authorId.equals(userId);
           JsonResult<User> userRes = userApiClient.getUserById(authorId);
           User user;
           if (!ResultUtils.isSuccess(userRes)) {
               user = User.Anonymous();
           } else {
               user = userRes.getData();
           }

           JsonResult<List<CountVo>> countRes = commonApiClient.getCountsByBizTypes(articleId,ARTICLE_COUNT_BIZ_TYPE);

           Map<String,Object> resultMap = Maps.newHashMapWithExpectedSize(4);
           resultMap.put("author",user);
           resultMap.put("article",article);
           resultMap.put("isAuthor",isAuthor);
           List<CountVo> countVos;
           if(ResultUtils.isSuccess(countRes)){
               countVos =countRes.getData();
           }else {
               countVos = getDefaultArticleCount(articleId);
           }
           for (CountVo count : countVos){
               if (count.getBizType() == CountBizType.ARTICLE_BROWSER.getCode()){
                   resultMap.put("browseCount",count.getBizCount());
               }
               if (count.getBizType() == CountBizType.ARTICLE_COMMENT.getCode()){
                   resultMap.put("commentCount",count.getBizCount());
               }
           }
           return ResultUtils.success(resultMap);
       }
       return articleRes;
   }

   private List<CountVo> getDefaultArticleCount(Long bizId){
       List<CountVo> result = new ArrayList<>(2);
       result.add(new CountVo(CountBizType.ARTICLE_BROWSER.getCode(),bizId,0));
       result.add(new CountVo(CountBizType.ARTICLE_COMMENT.getCode(),bizId,0));
       return result;
   }
}
