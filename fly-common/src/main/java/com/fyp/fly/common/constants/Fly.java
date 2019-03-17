package com.fyp.fly.common.constants;

/**
 * @author fyp
 * @crate 2019/3/9 0:04
 * @project fly
 */
public interface Fly {
    String SSO_COOKIE_KEY = "sso_auth";
    String WEB_COOKIE_KEY = "fly_auth";
    String WEB_COOKIE_USER_KEY = "fly_user";
    String WEB_ATTRIBUTE_USER_KEY = "headerUser";

    String WEB_CACHE_USER_KEY = "app:user:";
    Integer WEB_CACHE_USER_EXPIRE = 600;
    Integer WEB_TOKEN_EXPIRE = 604800;

    interface Status {
        int API_CODE_NOTFOUND = 1404;
        int API_CODE_INNERERROR = 1500;
    }
}
