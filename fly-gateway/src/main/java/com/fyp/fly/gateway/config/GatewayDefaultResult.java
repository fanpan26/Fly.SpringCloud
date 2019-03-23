package com.fyp.fly.gateway.config;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.common.utils.JSONUtils;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * @author fyp
 * @crate 2019/3/23 12:35
 * @project fly
 */
public class GatewayDefaultResult {

    public static final byte[] getDefaultResultBytes(){
        return DEFAULT_RESULT_BYTES;
    }

    private static final byte[] DEFAULT_RESULT_BYTES;
    static {
        JsonResult gatewayErrorResult = ResultUtils.newResult(1502, "系统内部异常");
        String resultString = JSONUtils.toJSONString(gatewayErrorResult);
        DEFAULT_RESULT_BYTES = resultString.getBytes();
    }
}
