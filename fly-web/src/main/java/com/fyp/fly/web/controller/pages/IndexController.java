package com.fyp.fly.web.controller.pages;

import com.fyp.fly.common.constants.Fly;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.common.result.api.SsoTicketApiResult;
import com.fyp.fly.common.utils.CookieUtils;
import com.fyp.fly.web.client.comment.CommentApiClient;
import com.fyp.fly.web.controller.biz.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fanpan26
 * */
@Controller
public class IndexController extends BaseController{


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CommentApiClient commentApiClient;

    @Value("${sso.url}")
    private String ssoUrl;

    /**
     * Fly社区首页：进行ticket校验
     * @param ticket SSO
     * */
    @GetMapping("/")
    public String index(@Nullable String ticket,@Nullable String redirect, HttpServletResponse response) {
        if (!StringUtils.isEmpty(ticket)) {
            String token = verifyTicket(ticket);
            //假冒伪劣 ticket 不予理会
            if (token != null) {
                CookieUtils.setCookie(response, Fly.WEB_COOKIE_KEY, token, Fly.WEB_TOKEN_EXPIRE);
            }
            return StringUtils.isEmpty(redirect) ? "redirect:/" : "redirect:" + redirect;
        }
        return "index";
    }

    @GetMapping("rank")
    public String userReplyRank(){
        JsonResult<Object> ranks = commentApiClient.getUserRank();
        if (ResultUtils.isSuccess(ranks)){
            request.setAttribute("userList",ranks.getData());
        }
        return "/partial/replyRank";
    }

    private String verifyTicket(String ticket){
        JsonResult<SsoTicketApiResult> response = restTemplate.exchange(ssoUrl+"/ticket/verify?ticket="+ticket,
                HttpMethod.GET, null, new ParameterizedTypeReference<JsonResult<SsoTicketApiResult>>() {}).getBody();
        if (ResultUtils.isSuccess(response)) {
            return response.getData().getToken();
        }
        return null;
    }
}
