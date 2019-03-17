package com.fyp.fly.common.dto;

/**
 * @author fyp
 * @crate 2019/3/17 10:04
 * @project fly
 *
 * 通用计数类
 */
public class CountDto {
    private long bizId;
    private int bizType;
    private int bizCount;

    public long getBizId() {
        return bizId;
    }

    public void setBizId(long bizId) {
        this.bizId = bizId;
    }

    public int getBizType() {
        return bizType;
    }

    public void setBizType(int bizType) {
        this.bizType = bizType;
    }

    public int getBizCount() {
        return bizCount;
    }

    public void setBizCount(int bizCount) {
        this.bizCount = bizCount;
    }

}
