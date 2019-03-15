package com.fyp.fly.common.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class CookieUtils {

    private static Cookie getCookieInternal(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }
    public static String getCookie(HttpServletRequest request, String name) {
        Cookie cookie = getCookieInternal(request,name);
        if(cookie!=null){
            return cookie.getValue();
        }
        return null;
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie cookie = getCookieInternal(request, name);
        if (cookie != null) {
            cookie.setMaxAge(0);
            cookie.setValue("");
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    public static void setCookie(HttpServletResponse response, String name, String value,int maxAge) {
        setCookie(response,name,value,"/",true,maxAge);
    }

    public static void setCookie(HttpServletResponse response, String name, String value,String path,boolean httpOnly,int maxAge) {
        Cookie cookie = new Cookie(name, EncodeUtils.encodeUrl(value));
        cookie.setPath(path);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}