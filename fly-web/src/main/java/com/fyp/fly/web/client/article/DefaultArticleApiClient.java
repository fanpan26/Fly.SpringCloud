package com.fyp.fly.web.client.article;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.web.client.AbstractApiClient;
import com.fyp.fly.web.config.FlyContext;
import com.fyp.fly.web.controller.form.ArticleDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;


/**
 * @author fyp
 * @crate 2019/3/16 13:06
 * @project fly
 */
@Service
public class DefaultArticleApiClient extends AbstractApiClient implements ArticleApiClient {


    @Value("${gateway.services.article}")
    private String serviceId;

    @Override
    protected String getServiceId() {
        return serviceId;
    }

    private String getAddUrl() {
        return buildApiUrl("article/");
    }
    private String getBrowseUrl(Long articleId) {
        return buildApiUrl("article/browse/"+articleId);
    }
    private String getRemoveUrl(Long articleId) {
        return buildApiUrl("article/remove/"+articleId);
    }

    private String getArticleUrl(Long articleId) {
        return buildAggregationUrl("aggregation/article/" + articleId + "?userId=" + FlyContext.getUserId());
    }

    @Override
    public JsonResult add(ArticleDto parameter) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("id", parameter.getId());
        parameters.add("title", parameter.getTitle());
        parameters.add("content", parameter.getContent());
        parameters.add("category", parameter.getCategory());
        parameters.add("experience", parameter.getExperience());

        return postForObjectWithFormHeader(getAddUrl(), parameters);
    }

    @Override
    public JsonResult<Map<String, Object>> getArticleById(Long articleId) {
        String url = getArticleUrl(articleId);
        return getForObject(url, new ParameterizedTypeReference<JsonResult<Map<String, Object>>>() {
        });
    }

    @Override
    public JsonResult browse(Long id) {
        String url = getBrowseUrl(id);
        return postForObject(url);
    }

    @Override
    public JsonResult remove(Long id) {
        String url = getRemoveUrl(id);
        return postForObject(url);
    }
}
