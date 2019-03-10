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
    String WEB_ATTRIBUTE_USER_KEY = "fly_current_user";
    Integer WEB_COOKIE_USER_EXPIRE = 604800;
}
