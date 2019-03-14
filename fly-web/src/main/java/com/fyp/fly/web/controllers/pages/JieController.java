package com.fyp.fly.web.controllers.pages;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.web.clients.base.BaseApiClient;
import com.fyp.fly.web.controllers.biz.BaseController;
import com.fyp.fly.web.controllers.parameters.PostParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fyp
 * @crate 2019/3/14 20:41
 * @project fly
 */
@Controller
@RequestMapping("/jie")
public class JieController extends BaseController{

    private static final String POST_PAGE_ATTRIBUTE_KEY = "jie";
    /**
     * 添加帖子
     */
    @GetMapping("/add")
    public String add() {
        java.util.Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
        if(map != null) {
            Object object = map.get(POST_PAGE_ATTRIBUTE_KEY);
            if (object != null) {
                request.setAttribute(POST_PAGE_ATTRIBUTE_KEY, object);
                return "/jie/add";
            }
        }
        request.setAttribute(POST_PAGE_ATTRIBUTE_KEY, PostParameter.newParameter());
        return "/jie/add";
    }

    /**
     * 帖子详情
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id) {
        return "/jie/detail";
    }

    /**
     * 帖子首页
     */
    @GetMapping("/index")
    public String index() {
        return "/jie/index";
    }


    @Autowired
    private BaseApiClient baseApiClient;
    /**
     * 发布一篇帖子
     * */
    @PostMapping("/post")
    public String post(PostParameter parameter,
                     RedirectAttributes redirect) throws IOException{

        String code = parameter.getVercode();
        JsonResult res = baseApiClient.validateCode(getUserId(), code);
        //invalid code
        if(!ResultUtils.isSuccess(res)){
            parameter.setAlert(true);
            parameter.setErrorMsg("验证码不正确");
            redirect.addFlashAttribute(POST_PAGE_ATTRIBUTE_KEY, parameter);
            return "redirect:/jie/add";
        }
        return "redirect:/";
    }
}
