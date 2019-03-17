package com.fyp.fly.services.article.service;

import com.fyp.fly.common.constants.Fly;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.services.article.domain.Article;
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
}
