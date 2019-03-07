package com.fyp.fly.sso.interceptors;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.common.tools.SafeEncoder;
import com.fyp.fly.sso.api.client.AccountApiClient;
import com.fyp.fly.sso.api.results.SsoTicketApiResult;
import com.fyp.fly.sso.config.Sso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author fyp
 * @crate 2019/3/7 22:59
 * @project fly
 */
@Component
public class GlobalInterceptor extends HandlerInterceptorAdapter {

    @Value("${fly.sso.default.redirect_url}")
    private String defaultRedirectUrl;

    @Autowired
    private AccountApiClient accountApiClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getAuthenticationToken(request);
        String from = getReferer(request);
        if (StringUtils.isEmpty(token)) {
            response.sendRedirect("/account/login?redirect_url=" + SafeEncoder.encodeUrl(from));
        } else {
            //TODO get ticket by token
            JsonResult<SsoTicketApiResult> ssoTicket = accountApiClient.getTicketByToken(token);
            if (ResultUtils.isSuccess(ssoTicket)) {
                response.sendRedirect(from + (from.indexOf("?") > -1 ? "&" : "?") + "ticket=" + ssoTicket.getData().getTicket());
            } else {
                response.sendRedirect("/account/login?redirect_url=" + getReferer(request));
            }
        }
        return false;
    }

    private String getReferer(HttpServletRequest request){
        Enumeration<String> referers = request.getHeaders(HttpHeaders.REFERER);
        String from = null;
        if (referers.hasMoreElements()) {
            from = referers.nextElement();
        }
        //TODO 判断referer 是否是本网站
       if (StringUtils.isEmpty(from)){
            from = defaultRedirectUrl;
       }
       return from;
    }

    protected String getAuthenticationToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Sso.COOKIE_KEY)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
