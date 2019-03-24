package com.fyp.fly.common.dto;

/**
 * @author fyp
 * @crate 2019/3/17 10:04
 * @project fly
 *
 * 通用计数类
 */
public class CountVo {

    public CountVo(){

    }
    public CountVo(int bizType, Long bizId, Integer bizCount) {
        this.bizType = bizType;
        this.bizId = bizId;
        this.bizCount = bizCount;
    }

    private Long bizId;
    private int bizType;
    private Integer bizCount;

    public long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
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

    public void setBizCount(Integer bizCount) {
        this.bizCount = bizCount;
    }

}
