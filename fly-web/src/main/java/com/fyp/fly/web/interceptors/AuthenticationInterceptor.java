package com.fyp.fly.web.interceptors;

import com.fyp.fly.common.constants.Fly;
import com.fyp.fly.common.dto.FlyUserDto;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.common.tools.CookieUtils;
import com.fyp.fly.common.tools.EncodeUtils;
import com.fyp.fly.common.tools.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author fyp
 * @crate 2019/3/9 0:01
 * @project fly
 */
@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    public AuthenticationInterceptor(){

    }
    @Value("${sso.url}")
    private String ssoUrl;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private ValueOperations<String, String> ops() {
        return redisTemplate.opsForValue();
    }

    private String cacheKey(Long userId){
        return Fly.WEB_CACHE_USER_KEY + userId;
    }


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String token = CookieUtils.getCookie(request, Fly.WEB_COOKIE_KEY);
        if (!StringUtils.isEmpty(token)) {
            String uid = CookieUtils.getCookie(request, Fly.WEB_COOKIE_USER_KEY);
            if (uid != null) {
                String userJson = ops().get(cacheKey(Long.valueOf(uid)));
                if (!StringUtils.isEmpty(userJson)) {
                    FlyUserDto user = JSONUtils.parseObject(userJson, FlyUserDto.class);
                    if (user != null) {
                        request.setAttribute(Fly.WEB_ATTRIBUTE_USER_KEY, user);
                        return true;
                    }
                }
            }
            //if no user info,check token
            JsonResult<FlyUserDto> userRes = getUserFromSsoApi(token);
            if (userRes != null) {
                String userJsonString = JSONUtils.toJSONString(userRes.getData());
                setCache(userRes.getData().getId(), userJsonString);
                CookieUtils.setCookie(response, Fly.WEB_COOKIE_USER_KEY, userRes.getData().getId() + "", Fly.WEB_TOKEN_EXPIRE);
                request.setAttribute(Fly.WEB_ATTRIBUTE_USER_KEY, userRes.getData());
            }
        }else{
            if (!(request.getRequestURI().equals("/")||request.getRequestURI().startsWith("/account"))){
                response.sendRedirect(ssoUrl+"?from=fly-web&url="+ EncodeUtils.encodeUrl(request.getRequestURI()));
                return false;
            }
        }
        return true;
    }

    private void setCache(Long userId,String userJsonString){
        ops().set(cacheKey(userId),userJsonString,Fly.WEB_CACHE_USER_EXPIRE, TimeUnit.SECONDS);
    }

    private JsonResult<FlyUserDto> getUserFromSsoApi(String token) {
        JsonResult<FlyUserDto> response = restTemplate.exchange(ssoUrl + "/user/info?token=" + token,
                HttpMethod.GET, null, new ParameterizedTypeReference<JsonResult<FlyUserDto>>() {
                }).getBody();
        if (ResultUtils.isSuccess(response)) {
            return response;
        }
        //TODO log the response error code and msg
        return null;
    }

}
