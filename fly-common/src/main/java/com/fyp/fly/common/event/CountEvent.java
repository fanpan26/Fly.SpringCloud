package com.fyp.fly.common.event;

import java.io.Serializable;

public class CountEvent implements Serializable {
    public CountEvent(){
        increment = true;
    }

    public boolean isIncrement() {
        return increment;
    }

    public void setIncrement(boolean increment) {
        this.increment = increment;
    }

    private boolean increment;
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
