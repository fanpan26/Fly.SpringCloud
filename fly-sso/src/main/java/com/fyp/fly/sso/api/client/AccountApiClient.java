package com.fyp.fly.sso.api.client;

import com.fyp.fly.common.api.result.JsonResult;
import com.fyp.fly.sso.api.results.SsoTicketApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public final class AccountApiClient {

    private static final String API_ACCOUNT_SERVICE_HOST = "http://localhost:8082";
    protected static final String API_LOGIN = "/account/login";

    private static final HttpHeaders POST_FORM_URLENCODED_HEADER = new HttpHeaders();
    static {
        POST_FORM_URLENCODED_HEADER.add("Content-Type", "application/x-www-form-urlencoded");
    }

    @Autowired
    private RestTemplate restTemplate;


    protected String getApiUrl(String url){
        return String.format("%s%s",API_ACCOUNT_SERVICE_HOST,url);
    }

    public JsonResult<SsoTicketApiResult> login(String name, String pwd) {
        MultiValueMap<String, Object> parameter = new LinkedMultiValueMap<>();
        parameter.add("name", name);
        parameter.add("pwd", pwd);

        JsonResult<SsoTicketApiResult> result = postForObjectWithFormHeader(getApiUrl(API_LOGIN), parameter, new ParameterizedTypeReference<JsonResult<SsoTicketApiResult>>() {

        });
        return result;
    }

    /**
     * 封装form表单的请求
     * */
    private  <T> JsonResult<T> postForObjectWithFormHeader(String apiUrl, MultiValueMap<String, Object> parameters, ParameterizedTypeReference<JsonResult<T>> parameterizedTypeReference) {
        HttpEntity<MultiValueMap<String, Object>> requestBody = new HttpEntity<>(parameters, POST_FORM_URLENCODED_HEADER);
        JsonResult<T> apiResult = restTemplate.exchange(apiUrl, HttpMethod.POST, requestBody, parameterizedTypeReference).getBody();
        return apiResult;
    }

}
