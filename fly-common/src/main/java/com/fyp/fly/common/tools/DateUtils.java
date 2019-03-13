package com.fyp.fly.common.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fyp
 * @crate 2019/3/13 23:11
 * @project fly
 */
public class DateUtils {
    public static String timeStamp2Date(long seconds, String format) {
        if (seconds <= 0){
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }
}
