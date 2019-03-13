package com.fyp.fly.services.user.cache;

/**
 * @author fyp
 * @crate 2019/3/13 20:57
 * @project fly
 */
public interface Cache {
    long CACHE_USER_EXPIRE = 604800;
    String CACHE_USER = "service:user:";
}
