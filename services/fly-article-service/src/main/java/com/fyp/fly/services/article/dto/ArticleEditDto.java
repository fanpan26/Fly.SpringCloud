package com.fyp.fly.services.article.dto;


import com.fyp.fly.common.dto.BaseDto;
import com.fyp.fly.services.article.domain.Article;
import org.apache.commons.lang.StringUtils;

/**
 * @author fyp
 * @crate 2019/3/16 7:52
 * @project fly
 */
public class ArticleEditDto extends BaseDto{

    private long id;
    private int category;
    private String title;
    private String content;
    private int experience;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    private long userId;

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

    public Article transfer(){
        Article article = new Article();
        if (id == 0) {
            id = System.currentTimeMillis();
        }
        article.setId(id);
        article.setTitle(title);
        article.setAuthor(userId);
        article.setCategory(category);
        article.setExperience(experience);
        article.setContent(content);
        return article;
    }

    @Override
    public String checkArguments() {
        if (errMsg == null) {
            if (StringUtils.isEmpty(title)) {
                errMsg = "标题不能为空";
            }
            if (StringUtils.isEmpty(content)) {
                errMsg = "内容不能为空";
            }
            if (category <= 0) {
                errMsg = "无效的类型";
            }
            if (experience <= 0) {
                experience = 20;
            }
        }
        return errMsg;
    }
}
