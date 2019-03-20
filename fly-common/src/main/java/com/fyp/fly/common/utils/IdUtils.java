package com.fyp.fly.common.utils;

public class IdUtils {
    public static Long next(Long userId) {
        Long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        String val = userId + (timestamp + "").substring(4);
        val = val.substring(2);

        return Long.valueOf(val);
    }
}
