package com.fyp.fly.common.dto;

/**
 * @author fyp
 * @crate 2019/3/10 23:36
 * @project fly
 */
public class FlyUserDto {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getVip() {
        return  vip;
    }

    public String getVipString(){
        return "VIP" + getVip();
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    private String name;
    private String avatar;
    private int vip;

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    private boolean auth;
}
