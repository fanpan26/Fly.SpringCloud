package com.fyp.fly.web.clients;

import com.fyp.fly.common.result.api.JsonResult;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

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

    protected JsonResult postForObject(RestTemplate restTemplate,String url) {
        return restTemplate.exchange(url,HttpMethod.POST,null,JsonResult.class).getBody();
    }

    protected InputStream getForInputStream(RestTemplate restTemplate,String url) {
        ResponseEntity<Resource> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, Resource.class);

        InputStream responseInputStream;
        try {
            responseInputStream = responseEntity.getBody().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseInputStream;
    }
}
