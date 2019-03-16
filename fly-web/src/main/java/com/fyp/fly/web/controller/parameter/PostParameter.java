package com.fyp.fly.web.controller.parameter;

import org.springframework.util.StringUtils;

/**
 * @author fyp
 * @crate 2019/3/14 22:22
 * @project fly
 */
public class PostParameter {

    private static final PostParameter defaultParameter = new PostParameter();
    public static PostParameter newParameter(){
        return defaultParameter;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    private boolean alert;
    private long id;
    private int category;
    private String title;
    private String content;

    public String getVercode() {
        return vercode;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

    private String vercode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    private int experience;

    private String errorMsg;

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        if (StringUtils.isEmpty(errorMsg)&&alert) {
            if (StringUtils.isEmpty(title)) {
                return "请填写标题";
            }
            if (StringUtils.isEmpty(content)) {
                return "请填写内容";
            }
        }
        return errorMsg;
    }
}
