package com.fyp.fly.services.account.domain;


/**
 * @author fyp
 * @crate 2019/3/6 21:08
 * @project fly
 */
public class JwtResult {
    private String token;
    private long expireAt;

    public static JwtResult tokenResult(String token,long expireSeconds){
        return new JwtResult(token,System.currentTimeMillis()/1000 + expireSeconds);
    }

    public JwtResult(String token,long expireAt){
        this.token = token;
        this.expireAt = expireAt;
    }

    public String getToken() {
        return token;
    }

    public long getExpireAt(){
        return  expireAt;
    }
}
