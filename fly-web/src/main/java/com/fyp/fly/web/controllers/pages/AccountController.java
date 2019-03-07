package com.fyp.fly.web.controllers.pages;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String redirectToSSO() {
        return "redirect:" + ssoUrl;
    }
}
