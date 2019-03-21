package com.fyp.fly.common.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author fyp
 * @crate 2019/3/13 23:11
 * @project fly
 */
public class DateUtils {
    public static String secondsToDate(long seconds) {
        return millisecondsToDate(seconds * 1000);
    }

    public static String millisecondsToDate(long milliseconds) {
        if (milliseconds <= 0) {
            return "";
        }
        return DateUtil.formatDate(DateUtil.date(milliseconds));
    }

    public static String offset(long milliseconds) {
        long now = System.currentTimeMillis();
        long offset = (now - milliseconds) / 1000;
        if (offset < 60) {
            return "刚刚";
        }
        if (offset < 3600) {
            return offset / 60 + "分钟前";
        }
        if (offset < 3600 * 24) {
            return offset / 3600 + "小时前";
        }
        if (offset < 3600 * 48) {
            return offset / 3600 + "小时前";
        }
        return millisecondsToDate(milliseconds);

    }
}
