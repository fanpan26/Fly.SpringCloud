package com.fyp.fly.web.clients.user;

import com.fyp.fly.common.result.api.JsonResult;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 * @author fyp
 * @crate 2019/3/13 21:57
 * @project fly
 */
public class AbstractApiClient {

    protected  <T> JsonResult<T> getForObject(RestTemplate restTemplate,String url,ParameterizedTypeReference<JsonResult<T>> parameterizedTypeReference){
        return restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                parameterizedTypeReference).getBody();
    }
}
