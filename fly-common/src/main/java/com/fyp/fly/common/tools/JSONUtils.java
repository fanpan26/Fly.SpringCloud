package com.fyp.fly.common.tools;

import com.alibaba.fastjson.JSON;

/**
 * @author fyp
 * @crate 2019/3/10 22:58
 * @project fly
 */
public final class JSONUtils {

    /**
     * JSON序列化
     * */
    public static String toJSONString(Object object){
        return JSON.toJSONString(object);
    }

    /**
     * JSON 反序列化
     * */
    public static <T> T parseObject(String json,Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

}
