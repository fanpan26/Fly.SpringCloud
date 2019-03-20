package com.fyp.fly.web.client.comment;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.web.client.AbstractApiClient;
import com.fyp.fly.web.controller.form.CommentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class DefaultCommentApiClient extends AbstractApiClient implements CommentApiClient {

    @Value("${gateway.services.comment}")
    private String serviceId;

    @Override
    protected String getServiceId() {
        return serviceId;
    }

    private String getAddUrl() {
        return buildApiUrl("comment/");
    }
    private String getDeleteUrl(Long id) {
        return buildApiUrl("comment/remove/"+id);
    }
    private String getContentUrl(Long id) {
        return buildApiUrl("comment/content/"+id);
    }

    @Override
    public JsonResult add(CommentDto comment) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        parameters.add("content", comment.getContent());
        parameters.add("artId", comment.getArtId());
        parameters.add("replyId", comment.getReplyId());

        return postForObjectWithFormHeader(getAddUrl(), parameters);
    }

    @Override
    public JsonResult remove(Long id) {
       String url = getDeleteUrl(id);
       return postForObject(url);
    }

    @Override
    public JsonResult getContent(Long id) {
        String url = getContentUrl(id);
        return getForObject(url, new ParameterizedTypeReference<JsonResult<String>>() {
        });
    }

    @Override
    public JsonResult updateContent(Long id,String content) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("content", content);
        return postForObjectWithFormHeader(getContentUrl(id), parameters);
    }
}
