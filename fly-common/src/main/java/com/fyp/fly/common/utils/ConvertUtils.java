package com.fyp.fly.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fyp
 * @crate 2019/3/16 13:48
 * @project fly
 */
public final class ConvertUtils {

    public static Map<String, Object> object2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> map = new HashMap<>(fields.length);
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
