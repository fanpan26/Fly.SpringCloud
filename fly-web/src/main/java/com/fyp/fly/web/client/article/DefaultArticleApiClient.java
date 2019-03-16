package com.fyp.fly.web.client.article;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.web.client.AbstractApiClient;
import com.fyp.fly.web.config.FlyContext;
import com.fyp.fly.web.controller.parameter.PostParameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


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

    private String getAddUrl(){
        return buildApiUrl("article/");
    }

    @Override
    public JsonResult add(PostParameter parameter) {
       Long userId = FlyContext.getUserId();
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("id", parameter.getId());
        parameters.add("title", parameter.getTitle());
        parameters.add("content", parameter.getContent());
        parameters.add("category", parameter.getCategory());
        parameters.add("experience", parameter.getExperience());

        return postForObjectWithFormHeader(getAddUrl(), parameters);
    }
}
