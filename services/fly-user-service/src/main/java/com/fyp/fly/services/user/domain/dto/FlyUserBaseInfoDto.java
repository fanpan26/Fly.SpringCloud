package com.fyp.fly.services.user.domain.dto;

import com.fyp.fly.services.user.domain.FlyUser;

/**
 * @author fyp
 * @crate 2019/3/13 20:11
 * @project fly
 */
public class FlyUserBaseInfoDto {

    public static FlyUserBaseInfoDto createByUser(FlyUser user){
        return new FlyUserBaseInfoDto(user.getName(),user.getAvatar(),user.getVip(),user.getAuth());
    }

    public FlyUserBaseInfoDto(String name,String avatar,int vip,boolean isAuth) {
        this.name = name;
        this.avatar = avatar;
        this.vip = vip;
        this.auth = isAuth;
    }

    public String getName() {
        return name;
    }
    public String getAvatar() {
        return avatar;
    }

    public int getVip() {
        return vip;
    }

    public boolean isAuth() {
        return auth;
    }

    private String name;
    private String avatar;
    private int vip;
    private boolean auth;
}
