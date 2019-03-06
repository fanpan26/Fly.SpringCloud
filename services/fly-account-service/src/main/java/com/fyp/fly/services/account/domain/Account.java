package com.fyp.fly.services.account.domain;

/**
 * @author fyp
 * @crate 2019/3/5 22:56
 * @project fly
 */
public class Account {
    public static final String ERROR_MSG_NOT_EXIST = "no account exists";
    public static final String ERROR_MSG_WRONG_PWD = "wrong password";

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
}
