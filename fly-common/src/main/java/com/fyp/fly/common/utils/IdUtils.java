package com.fyp.fly.common.utils;

/**
 * @author fyp
 * @crate 2019/3/23 17:48
 * @project fly
 */
public class IdUtils {
    public static final Long nextId(Integer bizType,Long userId){
        return System.currentTimeMillis();
    }
}
