package com.fyp.fly.sso.controllers;

import com.fyp.fly.common.api.result.JsonResult;
import com.fyp.fly.common.api.result.ResultUtils;
import com.fyp.fly.sso.api.client.AccountApiClient;
import com.fyp.fly.sso.api.results.SsoTicketApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Value("${fly.sso.default.redirect_url}")
    private String defaultRedirectUrl;

    private static final String REDIRECT_URL_SESSION_ATTRIBUTE = "redirectUrl";
    private static final String REDIRECT_TO_ERROR_MSG = "errMsg";

    @Autowired
    private AccountApiClient accountApiClient;
    /**
     * login page
     * */
    @GetMapping("/login")
    public String login(@Nullable @RequestParam("redirect_url") String redirectUrl,HttpServletRequest request) {
        java.util.Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
        if(map != null) {
            Object errMsg = map.get(REDIRECT_TO_ERROR_MSG);
            if (errMsg != null) {
                request.setAttribute(REDIRECT_TO_ERROR_MSG, errMsg);
            }
        }
        if (redirectUrl != null) {
            request.getSession().setAttribute(REDIRECT_URL_SESSION_ATTRIBUTE, redirectUrl);
        }
        return "login";
    }

    /**
     * 用户登录，gate-way-server/account/login
     * @param account 用户名
     * @param password 密码
     * @param code 验证码
     * @param request HttpServletRequest 对象
     * @return 登录成功直接跳转回 redirectUrl
     * */
    @PostMapping("/login")
    public String login(@RequestParam("account") String account,
                        @RequestParam("password") String password,
                        @RequestParam("code") String code,
                        HttpServletRequest request,
                        RedirectAttributes redirect) {

        JsonResult<SsoTicketApiResult> result = accountApiClient.login(account, password);

        if (ResultUtils.isSuccess(result)) {
            return "redirect:" + getRedirectUrl(request, result.getData().getTicket());
        } else {
            redirect.addFlashAttribute(REDIRECT_TO_ERROR_MSG, result.getMsg());
            Object redirectUrl = request.getSession().getAttribute(REDIRECT_URL_SESSION_ATTRIBUTE);
            return "redirect:login" + (StringUtils.isEmpty(redirectUrl) ? "" : "?redirect_url=" + redirectUrl);
        }
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
