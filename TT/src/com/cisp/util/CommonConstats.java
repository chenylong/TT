package com.cisp.util;

import java.util.HashMap;
import java.util.Map;

public class CommonConstats {
    public static Object getObject(Object key) {
        return map.get(key);
    }

    public static void setObject(Object key, Object value) {
        map.put(key, value);
    }
    private static Map map = new HashMap(100);
    public static final String APPLICATIONCONTEXT_NAME ="common.application";
}
