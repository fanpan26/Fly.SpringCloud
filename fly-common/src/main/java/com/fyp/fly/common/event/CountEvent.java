package com.fyp.fly.common.event;

import java.io.Serializable;

public class CountEvent implements Serializable {
    private Long bizId;

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    private Integer bizType;
}
