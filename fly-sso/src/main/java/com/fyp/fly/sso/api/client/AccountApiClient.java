package com.fyp.fly.sso.api.client;

import com.alibaba.fastjson.JSON;
import com.fyp.fly.common.dto.FlyUserDto;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.SsoTicketApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author fanpan26
 * */
public final class AccountApiClient {

    /**
     * eureka [account service] id:fly-account-service
     * */
    private static final String API_ACCOUNT_SERVICE_ID = "fly-account-service";

    private static final String API_LOGIN = "/account/login";
    private static final String API_TICKET = "/account/ticket";
    private static final String API_TICKET_VERIFY = "/account/ticket/verify";
    private static final String API_USER = "/account/user";

    private static final HttpHeaders POST_FORM_URLENCODED_HEADER = new HttpHeaders();
    static {
        POST_FORM_URLENCODED_HEADER.add("Content-Type", "application/x-www-form-urlencoded");
    }

    @Value("${fly.sso.gateway_url}")
    private String gateWayHost;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 构建请求地址。ex: http://localhost:9001/fly-account-service/account/login
     * */
    protected String getApiUrl(String address){
        return String.format("%s%s%s",gateWayHost, API_ACCOUNT_SERVICE_ID,address);
    }

    /**
     * 登录
     * */
    public JsonResult<SsoTicketApiResult> login(String name, String pwd) {
        MultiValueMap<String, Object> parameter = new LinkedMultiValueMap<>();
        parameter.add("name", name);
        parameter.add("pwd", pwd);

        JsonResult<SsoTicketApiResult> result = postForObjectWithFormHeader(getApiUrl(API_LOGIN), parameter, new ParameterizedTypeReference<JsonResult<SsoTicketApiResult>>() {

        });
        return result;
    }

    /**
     *
     * */
    public JsonResult<SsoTicketApiResult> getTicketByToken(String token) {
        return postForObject(getApiUrl(API_TICKET) + "?token=" + token, new ParameterizedTypeReference<JsonResult<SsoTicketApiResult>>() {

        });
    }

    public JsonResult<SsoTicketApiResult> verifyTicket(String ticket) {
        return postForObject(getApiUrl(API_TICKET_VERIFY) + "?ticket=" + ticket, new ParameterizedTypeReference<JsonResult<SsoTicketApiResult>>() {

        });
    }

    public JsonResult<FlyUserDto> getUser(String token) {
        return getForObject(getApiUrl(API_USER) + "?token=" + token, new ParameterizedTypeReference<JsonResult<FlyUserDto>>() {
        });
    }


    /**
     * 封装form表单的请求
     * */
    private  <T> JsonResult<T> postForObjectWithFormHeader(String apiUrl, MultiValueMap<String, Object> parameters, ParameterizedTypeReference<JsonResult<T>> parameterizedTypeReference) {
        HttpEntity<MultiValueMap<String, Object>> requestBody = new HttpEntity<>(parameters, POST_FORM_URLENCODED_HEADER);
        JsonResult<T> apiResult = restTemplate.exchange(apiUrl, HttpMethod.POST, requestBody, parameterizedTypeReference).getBody();
        return apiResult;
    }

    private  <T> JsonResult<T> getForObject(String apiUrl, ParameterizedTypeReference<JsonResult<T>> typeReference) {
        return executeForObject(apiUrl,typeReference,HttpMethod.GET);
    }
    private  <T> JsonResult<T> postForObject(String apiUrl, ParameterizedTypeReference<JsonResult<T>> typeReference) {
        return executeForObject(apiUrl,typeReference,HttpMethod.POST);
    }

    private  <T> JsonResult<T> executeForObject(String apiUrl, ParameterizedTypeReference<JsonResult<T>> typeReference,HttpMethod method) {
        JsonResult<T> apiResult = restTemplate.exchange(apiUrl, method, null, typeReference).getBody();
        return apiResult;
    }

}
