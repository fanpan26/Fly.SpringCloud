package com.fyp.fly.services.article.repository.mapper;

import com.fyp.fly.services.article.domain.Article;
import org.apache.ibatis.annotations.*;

/**
 * @author fyp
 * @crate 2019/3/16 7:40
 * @project fly
 */

public interface ArticleMapper {

    @Insert("INSERT INTO `fly_article`\n" +
            "(`id`,`author`,`title`,`category`,`content`,`top`,`special`,`del`,`create_at`,`update_at`,`experience`)\n" +
            "VALUES(#{id},#{author},#{title},#{category},#{content},#{top},#{special},#{del},#{createAt},#{updateAt},#{experience});")
    void add(Article article);

    @Select("SELECT id,author,title,category,content,top,special,closure,experience,create_at from fly_article WHERE id=#{id}")
    Article findById(@Param("id") Long id);
}
