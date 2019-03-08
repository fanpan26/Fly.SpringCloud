package com.fyp.fly.web.interceptors;

import com.fyp.fly.common.constants.Fly;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fyp
 * @crate 2019/3/9 0:01
 * @project fly
 */
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        return true;
    }

    protected String getAuthenticationToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Fly.WEB_COOKIE_KEY)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
