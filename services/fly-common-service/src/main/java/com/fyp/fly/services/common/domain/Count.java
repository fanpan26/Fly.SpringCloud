package com.fyp.fly.services.common.domain;

/**
 * @author fyp
 * @crate 2019/3/17 9:55
 * @project fly
 */
public class Count {
    private long id;
    private long bizId;
    private int bizType;
    private int bizCount;
    private long createAt;
    private long updateAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }
}
