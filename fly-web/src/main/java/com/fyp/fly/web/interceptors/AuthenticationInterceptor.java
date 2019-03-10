package com.fyp.fly.web.interceptors;

import com.fyp.fly.common.constants.Fly;
import com.fyp.fly.common.dto.FlyUserDto;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.common.result.api.SsoTicketApiResult;
import com.fyp.fly.common.tools.EncodeUtils;
import com.fyp.fly.common.tools.JSONUtils;
import com.fyp.fly.web.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fyp
 * @crate 2019/3/9 0:01
 * @project fly
 */
@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    public AuthenticationInterceptor(){
        System.out.println("AuthenticationInterceptor created");
    }
    @Value("${sso.url}")
    private String ssoUrl;


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String token = CookieUtils.getCookie(request, Fly.WEB_COOKIE_KEY);
        if (!StringUtils.isEmpty(token)) {
            String userJson = CookieUtils.getCookie(request, Fly.WEB_COOKIE_USER_KEY);
            if (!StringUtils.isEmpty(userJson)) {
                FlyUserDto user = JSONUtils.parseObject(EncodeUtils.decodeBase64(userJson), FlyUserDto.class);
                if (user != null) {
                    request.setAttribute(Fly.WEB_ATTRIBUTE_USER_KEY, user);
                    return true;
                }
            }
            //if no user info,check token
            JsonResult<FlyUserDto> userRes = getUserFromSsoApi(token);
            if (userRes != null) {
                String userCookieString = EncodeUtils.encodeBase64(JSONUtils.toJSONString(userRes.getData()));
                CookieUtils.setCookie(response, Fly.WEB_COOKIE_USER_KEY, userCookieString , Fly.WEB_COOKIE_USER_EXPIRE);
                request.setAttribute(Fly.WEB_ATTRIBUTE_USER_KEY, userRes.getData());
            }
        }
        return true;
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
