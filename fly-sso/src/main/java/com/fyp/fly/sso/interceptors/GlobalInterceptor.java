package com.fyp.fly.sso.interceptors;

import com.fyp.fly.common.constants.Fly;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.common.result.api.SsoTicketApiResult;
import com.fyp.fly.common.tools.EncodeUtils;
import com.fyp.fly.sso.api.client.AccountApiClient;
import com.fyp.fly.sso.config.SsoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fyp
 * @crate 2019/3/7 22:59
 * @project fly
 */
@Component
public class GlobalInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(GlobalInterceptor.class);

    @Value("${fly.sso.redirect_url.fly_web}")
    private String flyWebHost;

    @Value("${fly.sso.redirect_url.fly_admin}")
    private String flyAdminHost;

    @Autowired
    private AccountApiClient accountApiClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getAuthenticationToken(request);
        String from = request.getParameter("from");
        String url = request.getParameter("url");

        String fromUrl = SsoConfig.getUrl(from);
        if (StringUtils.isEmpty(token)) {
            response.sendRedirect("/account/login?redirect_url=" + EncodeUtils.encodeUrl(StringUtils.isEmpty(url)? fromUrl:(fromUrl+"?redirect="+url)));
        } else {
            JsonResult<SsoTicketApiResult> ssoTicket = accountApiClient.getTicketByToken(token);
            if (ResultUtils.isSuccess(ssoTicket)) {
                String location = fromUrl + (fromUrl.indexOf("?") > -1 ? "&" : "?") + "ticket=" + ssoTicket.getData().getTicket();
                if(!StringUtils.isEmpty(url)){
                    location+="&redirectUrl="+url;
                }
                response.sendRedirect(location);
            } else {
                response.sendRedirect("/account/login?redirect_url=" + EncodeUtils.encodeUrl(SsoConfig.getUrl(from)));
            }
        }
        return false;
    }


    protected String getAuthenticationToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Fly.SSO_COOKIE_KEY)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
