package com.fyp.fly.sso.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Value("${fly.sso.default.redirect_url}")
    private String defaultRedirectUrl;

    private static final String REDIRECT_URL_SESSION_ATTRIBUTE = "redirectUrl";

    @GetMapping("/login")
    public String login(@Nullable @RequestParam("redirect_url") String redirectUrl, HttpServletRequest request) {
        if (redirectUrl != null) {
            request.getSession().setAttribute(REDIRECT_URL_SESSION_ATTRIBUTE, redirectUrl);
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("account") String account,
                        @RequestParam("password") String password,
                        @RequestParam("code") String code,
                        HttpServletRequest request) {
        System.out.println("account:" + account + " password:" + password + " vercode:" + code);
        return "redirect:" + getRedirectUrl(request,"123456789");
    }

    private String getRedirectUrl(HttpServletRequest request,String ticket) {
        Object redirectUrlFromSession = request.getSession().getAttribute(REDIRECT_URL_SESSION_ATTRIBUTE);
        if (StringUtils.isEmpty(redirectUrlFromSession)) {
            redirectUrlFromSession = defaultRedirectUrl;
        }
        String redirectUrl = redirectUrlFromSession.toString();
        return redirectUrl + (redirectUrl.indexOf("?") > -1 ? "&" : "?") + "ticket=" + ticket;
    }
}
