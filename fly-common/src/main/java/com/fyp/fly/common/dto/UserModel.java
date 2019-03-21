package com.fyp.fly.common.dto;


import com.fyp.fly.common.utils.DateUtils;

/**
 * @author fyp
 * @crate 2019/3/10 23:36
 * @project fly
 */
public class UserModel {

    public String getVipString() {
        return "VIP" + getVip();
    }

    private long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private int sex;

    /**
     * 会员等级
     */
    private int vip;

    /**
     * 是否认证
     */
    private boolean auth;

    /**
     * 地址
     */
    private String address;

    /**
     * 签名
     */
    private String sign;

    /**
     * 认证信息
     */
    private String authStr;

    /**
     * 注册时间
     */
    private long createAt;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getVip() {
        return this.vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public boolean getAuth() {
        return this.auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAuthStr() {
        return this.authStr;
    }


    public void setAuthStr(String authStr) {
        this.authStr = authStr;
    }

    public long getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public String getCreateAtStr() {
        return DateUtils.millisecondsToDate(createAt);
    }


}
