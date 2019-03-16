package com.fyp.fly.web.clients.user;

import com.fyp.fly.common.dto.FlyUserDto;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.web.clients.AbstractApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author fyp
 * @crate 2019/3/13 21:34
 * @project fly
 */
@Service
public class DefaultUserApiClient extends AbstractApiClient implements UserApiClient{

    @Value("${gateway.services.user}")
    private String serviceId;

    private String getUserUrl(Long userId){
        return buildApiUrl("user/"+userId);
    }

    @Override
    public JsonResult<FlyUserDto> getUserById(Long userId) {
        return getForObject(getUserUrl(userId), new ParameterizedTypeReference<JsonResult<FlyUserDto>>() {
        });
    }

    @Override
    protected String getServiceId() {
        return serviceId;
    }
}
