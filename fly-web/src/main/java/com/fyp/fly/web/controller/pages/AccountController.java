package com.fyp.fly.web.controller.pages;

import com.fyp.fly.common.constants.Fly;
import com.fyp.fly.common.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fyp
 * @crate 2019/3/7 22:24
 * @project fly
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Value("${sso.url}")
    private String ssoUrl;

    @GetMapping("/login")
    public String ssoLogin() {
        return "redirect:" + ssoUrl + "?from=fly-web";
    }

    @GetMapping("/logout")
    public String ssoLogout(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtils.getCookie(request, Fly.WEB_COOKIE_KEY);
        CookieUtils.deleteCookie(request,response,Fly.WEB_COOKIE_KEY);
        CookieUtils.deleteCookie(request,response, Fly.WEB_COOKIE_USER_KEY);
        return "redirect:" + ssoUrl + "/account/logout?token=" + token + "&from=fly-web";
    }
}
