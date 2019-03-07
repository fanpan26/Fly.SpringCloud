package com.fyp.fly.sso.controllers;

import com.fyp.fly.sso.api.client.AccountApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

/**
 * @author fyp
 * @crate 2019/3/7 22:27
 * @project fly
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "login";
    }
}
