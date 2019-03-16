package com.fyp.fly.services.article.service;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.services.article.dto.ArticleEditDto;
import com.fyp.fly.services.article.repository.mapper.ArticleMapper;
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
}
