package com.fyp.fly.services.base.controllers;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author fyp
 * @crate 2019/3/14 21:19
 * @project fly
 */
@RestController
@RequestMapping("/validate")
public class ValidateController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String CACHE_KEY_VALIDATE_CODE = "services:base:code:";

    private void setCache(String code,Long userId) {
        redisTemplate.opsForValue().set(CACHE_KEY_VALIDATE_CODE + userId, code);
    }

    private boolean validate(String code,Long userId) {
        if (StringUtils.isEmpty(code)) {
            return false;
        }
        String key = CACHE_KEY_VALIDATE_CODE + userId;
        String cacheCode = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        return code.equals(cacheCode);
    }

    /**
     * 生成验证码
     * */
    @GetMapping("/code/{userId}")
    public void getCode(HttpServletResponse response, @PathVariable("userId") Long userId) throws IOException {
        ICaptcha captcha = CaptchaUtil.createLineCaptcha(100, 40);
        String code = captcha.getCode();
        setCache(code, userId);
        captcha.write(response.getOutputStream());
    }

    /**
     * 校验验证码是否正确
     * */
    @PostMapping("/code/{userId}")
    public JsonResult validateCode(@PathVariable("userId") Long userId, String code) throws IOException {
        boolean isValid = validate(code, userId);
        return isValid ? ResultUtils.success() : ResultUtils.failed();
    }
}
