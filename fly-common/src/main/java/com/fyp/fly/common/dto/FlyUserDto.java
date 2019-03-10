package com.fyp.fly.common.dto;

/**
 * @author fyp
 * @crate 2019/3/10 23:36
 * @project fly
 */
public class FlyUserDto {
    private long id;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    private String name;
    private String avatar;
    private int vip;
}
