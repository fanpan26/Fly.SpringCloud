package com.fyp.fly.services.article.service;

import com.fyp.fly.common.constants.Fly;
import com.fyp.fly.common.enums.CountBizType;
import com.fyp.fly.common.event.CountEvent;
import com.fyp.fly.common.event.FlyEvent;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.common.utils.JSONUtils;
import com.fyp.fly.services.article.domain.Article;
import com.fyp.fly.services.article.dto.ArticleEditDto;
import com.fyp.fly.services.article.repository.mapper.ArticleMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fyp
 * @crate 2019/3/16 7:49
 * @project fly
 */
@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public JsonResult add(ArticleEditDto article) {
        if (article == null){
            throw new NullPointerException("article");
        }
        if (article.isValid()){
            articleMapper.add(article.transfer());
            return ResultUtils.success();
        }else{
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
}
