package com.fyp.fly.services.user.domain;

import java.io.Serializable;

/**
 * @author fyp
 * @crate 2019/3/10 22:37
 * @project fly
 */
public class UserInfo implements Serializable{

    public UserInfo(){
        id=1;
        name="test";
        vip =1;
        avatar="https://tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg";
    }

    private long id;
    private String name;
    private int vip;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String avatar;

}
