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
    public static String timeStamp2Date(long seconds) {
        if (seconds <= 0) {
            return "";
        }
        return DateUtil.formatDate(DateUtil.date(seconds * 1000));
    }
}
