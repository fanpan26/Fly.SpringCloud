package com.fyp.fly.services.article.repository.mapper;

import com.fyp.fly.services.article.domain.Article;
import org.apache.ibatis.annotations.*;

/**
 * @author fyp
 * @crate 2019/3/16 7:40
 * @project fly
 */

public interface ArticleMapper {

    @Insert("INSERT INTO `fly`.`fly_article`\n" +
            "(`id`,`author`,`title`,`category`,`content`,`top`,`special`,`del`,`create_at`,`update_at`)\n" +
            "VALUES(#{id},#{author},#{title},#{category},#{content},#{top},#{special},#{del},#{createAt},#{updateAt});")
    void add(Article article);
}
