package com.fyp.fly.services.article.repository.mapper;

import com.fyp.fly.services.article.domain.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    @Select("SELECT id,author,title,category,content,top,special,closure,experience,create_at from fly_article WHERE id=#{id} and del=0")
    @Results({
            @Result(property = "createAt",column="create_at")
    })
    Article findById(@Param("id") Long id);

    @Select("SELECT id,title,top,special,create_at FROM fly_article where author=#{userId} and del=0 order by create_at desc limit 10")
    @Results({
            @Result(property = "createAt",column="create_at")
    })
    List<Article> getListByUserId(@Param("userId") Long userId);



   @Update("update fly_article set del=1 where id=#{id}")
    void delete(@Param("id") Long id);
}
