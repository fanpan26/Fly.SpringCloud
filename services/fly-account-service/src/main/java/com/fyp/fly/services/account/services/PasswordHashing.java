package com.fyp.fly.services.account.services;

/**
 * @author fyp
 * @crate 2019/3/7 21:55
 * @project fly
 */
public interface PasswordHashing {
    /**
     * 密码加盐加密
     * */
    String hash(String name, String password);
}
