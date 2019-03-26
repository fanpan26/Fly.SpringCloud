package com.fyp.fly.services.article.service;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.fyp.fly.common.constants.Fly;
import com.fyp.fly.common.dto.CountVo;
import com.fyp.fly.common.enums.CountBizType;
import com.fyp.fly.common.event.CountEvent;
import com.fyp.fly.common.event.FlyEvent;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.common.utils.JSONUtils;
import com.fyp.fly.services.article.client.CountFeignClient;
import com.fyp.fly.services.article.domain.Article;
import com.fyp.fly.services.article.domain.dto.ArticleEditDto;
import com.fyp.fly.services.article.domain.vo.ArticleTopVo;
import com.fyp.fly.services.article.domain.vo.ArticleUserRecentPublishVo;
import com.fyp.fly.services.article.repository.mapper.ArticleMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author fyp
 * @crate 2019/3/16 7:49
 * @project fly
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private static final String CACHE_ARTICLE_LIST_PREFIX = "service:article:list_";
    private static final String CACHE_ARTICLE_USER_LIST_PREFIX = "service:article:user_recent_";


    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CountFeignClient countFeignClient;

    @Autowired
    private HashOperations<String, String, String> hashOps;

    @Autowired
    private ValueOperations<String, String> valueOps;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public JsonResult add(ArticleEditDto article) {
        if (article == null) {
            throw new NullPointerException("article");
        }
        if (article.isValid()) {
            Article art = article.transfer();
            articleMapper.add(art);
            //add cache
            addListCache(art);
            redisTemplate.expire(getCacheArticleListKey(), 7, TimeUnit.DAYS);
            //remove recently published cache
            removeRecentPublishedCache(article.getUserId());
            return ResultUtils.success();
        } else {
            return ResultUtils.failed(article.checkArguments());
        }
    }

    @Override
    public JsonResult findOneById(Long id) {
        if (id == null || id == 0) {
            throw new IllegalArgumentException("id");
        }
        Article article = articleMapper.findById(id);
        if (article == null || article.getDel()) {
            return ResultUtils.failed(Fly.Status.API_CODE_NOTFOUND, "帖子不存在");
        }
        return ResultUtils.success(article);
    }

    @Override
    public JsonResult browse(Long id) {
        CountEvent event = new CountEvent();
        event.setBizId(id);
        event.setBizType(CountBizType.ARTICLE_BROWSER.getCode());
        rabbitTemplate.convertAndSend(FlyEvent.SERVICE_COMMON_EXCHANGE, FlyEvent.SERVICE_ARTICLE_COUNT_EVENT, JSONUtils.toJSONString(event));
        return ResultUtils.success();
    }

    @Override
    public JsonResult delete(Long id, Long userId) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id");
        }
        articleMapper.delete(id);
        hashOps.delete(getCacheArticleListKey(), id.toString());
        removeRecentPublishedCache(userId);
        return ResultUtils.success();
    }

    @Override
    public JsonResult getTopNCommentList(int top) {

        List<ArticleTopVo> resultList = new ArrayList<>();
        JsonResult<List<CountVo>> countRes = countFeignClient.getTopNCountsByBizType(CountBizType.ARTICLE_COMMENT.getCode(), 0, top);
        if (ResultUtils.isSuccess(countRes)) {
            List<CountVo> counts = countRes.getData();
            //组装文章标题数据
            List<String> ids = counts.stream().map(x -> String.valueOf(x.getBizId())).collect(Collectors.toList());
            List<String> articleJsons = hashOps.multiGet(getCacheArticleListKey(), ids);
            List<Article> articles;
            if (articleJsons != null && articleJsons.size() > 0) {
                articles = articleJsons.stream().map(x -> JSONUtils.parseObject(x, Article.class)).collect(Collectors.toList());
            } else {
               throw new IllegalStateException("no articles in cache");
            }
            if (articles.size() > 0) {
                for (CountVo count : counts) {
                    ArticleTopVo articleTopVo = new ArticleTopVo();
                    articleTopVo.setId(count.getBizId());
                    Optional<Article> article = articles.stream().filter(a -> {
                        if (a == null) {
                            return false;
                        }
                        return Objects.equals(a.getId(), count.getBizId());
                    }).findFirst();
                    if (article.isPresent()) {
                        articleTopVo.setTitle(article.get().getTitle());
                        articleTopVo.setCount(count.getBizCount());
                        resultList.add(articleTopVo);
                    }
                }
            }
        }

        return ResultUtils.success(resultList);
    }

    /**
     * 获取用户最近发布列表
     *
     * @param userId
     */
    @Override
    public JsonResult getRecentPublishedByUserId(Long userId) {

        String result = valueOps.get(CACHE_ARTICLE_USER_LIST_PREFIX + userId);
        if (result != null) {
            return ResultUtils.success(JSONUtils.parseArray(result, ArticleUserRecentPublishVo.class));
        }

        List<ArticleUserRecentPublishVo> publishVos = new ArrayList<>(10);
        List<Article> articles = articleMapper.getListByUserId(userId);
        if (articles.size() > 0) {
            List<Long> bizIds = articles.stream().map(x -> x.getId()).collect(Collectors.toList());
            List<CountVo> browses = countFeignClient.getCountsByBizIds(CountBizType.ARTICLE_BROWSER.getCode(), bizIds).getData();
            List<CountVo> comments = countFeignClient.getCountsByBizIds(CountBizType.ARTICLE_COMMENT.getCode(), bizIds).getData();

            publishVos = articles.stream().map(x -> {
                Optional<CountVo> currentBrowseVo = browses.stream().filter(b -> Objects.equals(b.getBizId(), x.getId())).findFirst();
                Optional<CountVo> currentCommentVo = comments.stream().filter(b -> Objects.equals(b.getBizId(), x.getId())).findFirst();
                int browseCount = currentBrowseVo.isPresent() ? currentBrowseVo.get().getBizCount() : 0;
                int commentCount = currentCommentVo.isPresent() ? currentCommentVo.get().getBizCount() : 0;
                ArticleUserRecentPublishVo vo =
                        new ArticleUserRecentPublishVo(x.getId(),
                                x.getSpecial(),
                                x.getTop(),
                                x.getTitle(),
                                x.getCreateAt(),
                                browseCount,
                                commentCount);
                return vo;
            }).collect(Collectors.toList());
        }
        valueOps.set(CACHE_ARTICLE_USER_LIST_PREFIX + userId, JSONUtils.toJSONString(publishVos), 30, TimeUnit.MINUTES);
        return ResultUtils.success(publishVos);
    }

    //本周热议，所以，缓存key以每周区分，
    private String getCacheArticleListKey() {
        return CACHE_ARTICLE_LIST_PREFIX;
    }

    private void removeRecentPublishedCache(Long userId) {
        redisTemplate.delete(CACHE_ARTICLE_USER_LIST_PREFIX + userId);
    }

    private void addListCache(Article article) {
        hashOps.put(getCacheArticleListKey(), String.valueOf(article.getId()), JSONUtils.toJSONString(article));
    }
}
