package com.fyp.fly.web.controller.pages;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.web.client.article.ArticleApiClient;
import com.fyp.fly.web.client.base.BaseApiClient;
import com.fyp.fly.web.controller.biz.BaseController;
import com.fyp.fly.web.controller.form.ArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author fyp
 * @crate 2019/3/14 20:41
 * @project fly
 */
@Controller
@RequestMapping("/jie")
public class JieController extends BaseController{

    private static final String POST_PAGE_ATTRIBUTE_KEY = "jie";
    private static final String ARTICLE_PAGE_ATTRIBUTE_KEY = "article";
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
        request.setAttribute(POST_PAGE_ATTRIBUTE_KEY, ArticleDto.newParameter());
        return "/jie/add";
    }

    /**
     * 帖子详情
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id) {
        if (id == null) {
            return "index";
        }
        JsonResult<Map<String, Object>> articleRes = articleApiClient.getArticleById(id);
        if (ResultUtils.isSuccess(articleRes)) {
            //add browse count
            articleApiClient.browse(id);
            request.setAttribute(ARTICLE_PAGE_ATTRIBUTE_KEY, articleRes.getData());
            return "/jie/detail";
        } else {
            return "404";
        }
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

    @Autowired
    private ArticleApiClient articleApiClient;
    /**
     * 发布一篇帖子
     * */
    @PostMapping("/post")
    public String addArticle(ArticleDto parameter,
                             RedirectAttributes redirect){

        String code = parameter.getVercode();
        JsonResult res = baseApiClient.validateCode(code);
        //invalid code
        if (!ResultUtils.isSuccess(res)) {
            return postError(parameter, "验证码不正确", redirect);
        }
        JsonResult result = articleApiClient.add(parameter);
        if (ResultUtils.isSuccess(result)) {
            return "redirect:/";
        } else {
            return postError(parameter, result.getMsg(), redirect);
        }
    }


    private String postError(ArticleDto parameter, String errorMessage, RedirectAttributes redirect) {
        parameter.setAlert(true);
        parameter.setErrorMsg(errorMessage);
        redirect.addFlashAttribute(POST_PAGE_ATTRIBUTE_KEY, parameter);
        return "redirect:/jie/add";
    }
}
