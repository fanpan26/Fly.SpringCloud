package com.fyp.fly.services.article.service;

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
import com.fyp.fly.services.article.repository.mapper.ArticleMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author fyp
 * @crate 2019/3/16 7:49
 * @project fly
 */
@Service
public class ArticleServiceImpl implements ArticleService{

    private static final String CACHE_ARTICLE_LIST_PREFIX = "service:article:list_";


    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CountFeignClient countFeignClient;

    @Autowired
    private HashOperations<String,String,String> hashOps;

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
            hashOps.put(getCacheArticleListKey(), String.valueOf(art.getId()), JSONUtils.toJSONString(art));
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
        if (article == null || article.getDel()){
            return ResultUtils.failed(Fly.Status.API_CODE_NOTFOUND,"帖子不存在");
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
    public JsonResult delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id");
        }
        articleMapper.delete(id);
        hashOps.delete(getCacheArticleListKey(), id.toString());
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
            if (articleJsons != null && articleJsons.size() > 0) {
                List<Article> articles = articleJsons.stream().map(x -> JSONUtils.parseObject(x, Article.class)).collect(Collectors.toList());

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

    private String getCacheArticleListKey() {
        return CACHE_ARTICLE_LIST_PREFIX + 0;
    }
}
