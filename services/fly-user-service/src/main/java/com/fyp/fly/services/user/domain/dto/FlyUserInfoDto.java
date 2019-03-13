package com.fyp.fly.services.user.domain.dto;

import com.fyp.fly.services.user.domain.FlyUser;

/**
 * @author fyp
 * @crate 2019/3/13 21:35
 * @project fly
 */
public class FlyUserInfoDto {

    public static FlyUserInfoDto createByUser(FlyUser user) {
        FlyUserInfoDto dto = new FlyUserInfoDto();
        if(user == null){
            return dto;
        }
        dto.id = user.getId();
        dto.address = user.getAddress();
        dto.auth = user.getAuth();
        dto.authStr = user.getAuthStr();
        dto.avatar = user.getAvatar();
        dto.name = user.getName();
        dto.sex = user.getSex();
        dto.createAt = user.getCreateAt();
        dto.sign = user.getSign();
        dto.vip = user.getVip();
        return dto;
    }

    private long id;

    /**
     * 姓名
     * */
    private String name;

    /**
     * 头像
     * */
    private String avatar;

    /**
     * 性别
     * */
    private int sex;

    /**
     * 会员等级
     * */
    private int vip;

    /**
     * 是否认证
     * */
    private boolean auth;

    /**
     * 地址
     * */
    private String address;

    /**
     * 签名
     * */
    private String sign;

    /**
     * 认证信息
     * */
    private String authStr;

    /**
     * 注册时间
     * */
    private long createAt;

    public long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public int getSex() {
        return this.sex;
    }
    public int getVip() {
        return this.vip;
    }
    public boolean getAuth() {
        return this.auth;
    }
    public String getAddress() {
        return this.address;
    }
    public String getSign() {
        return this.sign;
    }
    public String getAuthStr() {
        return this.authStr;
    }
    public long getCreateAt() {
        return this.createAt;
    }

}
