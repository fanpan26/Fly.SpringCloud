package com.fyp.fly.gateway.client.domain;

import com.fyp.fly.common.utils.DateUtils;

/**
 * @author fyp
 * @crate 2019/3/17 21:23
 * @project fly
 */
public class Article{
    public Article() {
        this.createAt = System.currentTimeMillis();
        this.updateAt = this.createAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAuthor() {
        return author;
    }

    public void setAuthor(long author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategory() {
        return category;
    }

    public String getCategoryStr(){
       return ArticleCategory.nameOfCategory(category);
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean getClosure(){
        return closure;
    }

    public void setClosure(boolean closure){
        this.closure = closure;
    }

    public boolean getSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public boolean getDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public String getCreateAtStr(){
        return DateUtils.millisecondsToDate(createAt);
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }
    public int getExperience(){
        return experience;
    }
    public void setExperience(int experience){
        this.experience= experience;
    }

    private int experience;

    private  long  id;

    private  long  author;

    private  String  title;

    private  int  category;

    private  String  content;

    private  boolean  top;

    private  boolean  special;

    private boolean closure;
    private  boolean  del;

    private  long  createAt;

    private  long  updateAt;

}