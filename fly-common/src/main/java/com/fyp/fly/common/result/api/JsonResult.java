package com.fyp.fly.common.result.api;

/**
 * @author fyp
 * @crate 2019/3/5 22:28
 * @project fly
 */
public class JsonResult<T> {

    public JsonResult(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult(int code, String msg){
        this(code,msg,null);
    }

    public JsonResult(int code){
        this(code,null,null);
    }

    public JsonResult(){
        this(0,"ok",null);
    }
    public JsonResult(T data){
        this(0,"ok",data);
    }

    private int code;
    private String msg;

    public T getData() {
        return data;
    }

    private T data;

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}
