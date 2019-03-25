package com.fyp.fly.common.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

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

    public static byte[] toJSONBytes(Object object){
        return JSON.toJSONBytes(object);
    }

    /**
     * JSON 反序列化
     * */
    public static <T> T parseObject(String json,Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> T parseObject(byte[] bytes,Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }
}
