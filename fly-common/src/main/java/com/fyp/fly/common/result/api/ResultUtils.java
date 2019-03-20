package com.fyp.fly.common.result.api;

/**
 * @author fyp
 * @crate 2019/3/5 22:32
 * @project fly
 */
public final class ResultUtils {

    private static final JsonResult DEFAULT_SUCCESS = new JsonResult();
    private static final JsonResult DEFAULT_FAILED = new JsonResult(-1, "error");

    public static JsonResult success() {
        return DEFAULT_SUCCESS;
    }

    public static JsonResult success(String msg) {
        return new JsonResult(0, msg);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<>(data);
    }


    public static JsonResult failed() {
        return DEFAULT_FAILED;
    }

    public static JsonResult failed(String msg) {
        return failed(-1, msg);
    }

    public static JsonResult failed(int code, String msg) {
        return newResult(code, msg);
    }

    public static boolean isSuccess(JsonResult result) {
        return result != null && result.getCode() == 0;
    }

    public static JsonResult newResult(int code, String msg) {
        return new JsonResult(code, msg);
    }

    public static JsonResult stringResult(String data) {
        return new JsonResult(0, "ok", data);
    }

}
