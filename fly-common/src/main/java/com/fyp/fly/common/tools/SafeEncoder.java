package com.fyp.fly.common.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public final class SafeEncoder {

    private static final String CHAR_SET = "UTF-8";

    public static String encodeUrl(String url) {
        try {
            return URLEncoder.encode(url, CHAR_SET);
        } catch (UnsupportedEncodingException ex) {
            return url;
        }
    }
}
