package com.fyp.fly.web.clients.base;

import com.fyp.fly.common.result.api.JsonResult;

import java.io.InputStream;

/**
 * @author fyp
 * @crate 2019/3/14 21:39
 * @project fly
 */
public interface BaseApiClient {
    InputStream getValidateCode(Long userId);
    JsonResult validateCode(Long userId,String code);
}