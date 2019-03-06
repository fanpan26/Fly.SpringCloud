package com.fyp.fly.services.account.domain;

/**
 * @author fyp
 * @crate 2019/3/5 22:56
 * @project fly
 */
public class Account {

    public static boolean notExists(final Account account){
        return account == null;
    }

    public boolean isCorrectPassword(final String userInputPwd){
        return loginPwd.equals(userInputPwd);
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String loginName;
    private String loginPwd;
    private long id;

    @Override
    public String toString(){
        return "loginName:"+loginName
                +".loginPwd:"+loginPwd;
    }

    public enum AccountType {

        /**
         * 正常
         */
        OK(0, "ok"),
        /**
         * 账户不存在
         */
        NOT_EXISTS(10001, "账户不存在"),
        /**
         * 密码不正确
         */
        WRONG_PASSWORD(10002, "密码错误"),
        /**
         * 密码错误次数超出限制
         */
        WRONG_PASSWORD_LIMIT(10003, "密码错误次数过多，账号已暂时锁定，请稍候在试"),
        /**
         * 账户已经被锁定
         */
        LOCKED(10004, "账号已暂时锁定，请稍后再试"),
        /**
         * 账号被拉黑
         * */
        BLACKED(10005,"账号已被禁止登录"),
        /**
         * 未登录
         * */
        OFFLINE(10006,"未登录");
        AccountType(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
