package com.fyp.fly.web.client.base;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.web.client.AbstractApiClient;
import com.fyp.fly.web.config.FlyContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        return buildApiUrl("validate/code/"+userId);
    }

    private String getValidateCodeUrl(Long userId,String code) {
        return buildApiUrl("validate/code/" + userId + "?code=" + code);
    }


    @Override
    public InputStream getValidateCode() {
        return getForInputStream(getCodeUrl(FlyContext.getUserId()));
    }

    @Override
    public JsonResult validateCode(String code) {
        return postForObject(getValidateCodeUrl(FlyContext.getUserId(), code));
    }

    @Override
    protected String getServiceId() {
        return serviceId;
    }
}
