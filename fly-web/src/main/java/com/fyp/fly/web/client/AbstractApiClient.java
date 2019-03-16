package com.fyp.fly.web.client;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.web.config.FlyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author fyp
 * @crate 2019/3/13 21:57
 * @project fly
 */
public abstract class AbstractApiClient {

    @Value("${gateway.url}")
    private String gateWayUrl;

    @Autowired
    protected RestTemplate restTemplate;

    protected abstract String getServiceId();

    private static final HttpHeaders POST_FORM_URLENCODED_HEADER = new HttpHeaders();
    static {
        POST_FORM_URLENCODED_HEADER.add("Content-Type", "application/x-www-form-urlencoded");
    }
    /**
     * 组装URL  http://gateway/serviceId/url
     * */
    protected final String buildApiUrl(String url) {
        return String.format("%s/%s/%s", gateWayUrl, getServiceId(),url);
    }

    protected  <T> JsonResult<T> getForObject(String url,ParameterizedTypeReference<JsonResult<T>> parameterizedTypeReference){
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                parameterizedTypeReference).getBody();
    }

    protected JsonResult postForObject(String url) {
        return restTemplate.exchange(url,HttpMethod.POST,null,JsonResult.class).getBody();
    }

    protected InputStream getForInputStream(String url) {
        ResponseEntity<Resource> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, Resource.class);

        InputStream responseInputStream;
        try {
            responseInputStream = responseEntity.getBody().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseInputStream;
    }


    protected  JsonResult postForObjectWithFormHeader(String apiUrl, MultiValueMap<String, Object> parameters) {
        if (parameters == null){
            throw new NullPointerException("parameters");
        }
        //add global userId
        parameters.add("userId", FlyContext.getUserId());
        HttpEntity<MultiValueMap<String, Object>> requestBody = new HttpEntity<>(parameters, POST_FORM_URLENCODED_HEADER);
        JsonResult apiResult = restTemplate.exchange(apiUrl, HttpMethod.POST, requestBody, new  ParameterizedTypeReference<JsonResult>(){

        }).getBody();
        return apiResult;
    }

}
