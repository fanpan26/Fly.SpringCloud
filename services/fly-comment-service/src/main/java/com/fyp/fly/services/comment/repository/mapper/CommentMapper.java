package com.fyp.fly.services.comment.repository.mapper;

import com.fyp.fly.services.comment.domain.Comment;
import org.apache.ibatis.annotations.*;

public interface CommentMapper {

    @Insert("INSERT INTO `fly_comment`(`id`,`uid`,`art_id`,`content`,`create_at`,`adopt`,`reply_id`)VALUES" +
            "(#{id},#{uid},#{artId},#{content},#{createAt},#{adopt},#{replyId})")
    void add(Comment comment);

    @Delete("DELETE FROM `fly_comment` WHERE id=#{id}")
    void delete(@Param("id") Long id);

    @Select("SELECT content FROM `fly_comment` where Id=#{id}")
    String getContentById(@Param("id") Long id);

    @Select("SELECT art_id FROM `fly_comment` where Id=#{id}")
    Long getArtIdById(@Param("id") Long id);

    @Update("UPDATE `fly_comment` SET content=#{content} WHERE `id`=#{id} and `uid`=#{uid}")
    void updateContent(Long id,Long uid,String content);
}
