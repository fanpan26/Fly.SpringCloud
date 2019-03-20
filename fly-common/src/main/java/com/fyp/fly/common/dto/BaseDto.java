package com.fyp.fly.common.dto;

/**
 * @author fyp
 * @crate 2019/3/16 7:55
 * @project fly
 */
public abstract class BaseDto {

    protected String errMsg;
    public final boolean isValid() {
        errMsg = checkArguments();
        return errMsg == null || errMsg.length() == 0;
    }

    public final String getErrMsg(){
        return errMsg;
    }

    public abstract String checkArguments();
}
