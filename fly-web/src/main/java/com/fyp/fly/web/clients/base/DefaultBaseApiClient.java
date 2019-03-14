package com.fyp.fly.web.clients.base;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.web.clients.AbstractApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;

/**
 * @author fyp
 * @crate 2019/3/14 21:40
 * @project fly
 */
@Service
public class DefaultBaseApiClient extends AbstractApiClient implements BaseApiClient {

    @Value("${gateway.url}")
    private String gateWayUrl;

    @Value("${gateway.services.base}")
    private String serviceId;

    private String getCodeUrl(Long userId){
        return String.format("%s/%s/%s/%d",gateWayUrl,serviceId,"validate/code",userId);
    }

    private String getValidateCodeUrl(Long userId,String code){
        return String.format("%s/%s/%s/%d?code=%s",gateWayUrl,serviceId,"validate/code",userId,code);
    }

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public InputStream getValidateCode(Long userId) {
        return getForInputStream(restTemplate,getCodeUrl(userId));
    }

    @Override
    public JsonResult validateCode(Long userId, String code) {
        return postForObject(restTemplate, getValidateCodeUrl(userId, code));
    }
}
