package com.fyp.fly.services.comment.repository.mapper;

import com.fyp.fly.services.comment.domain.Comment;
import org.apache.ibatis.annotations.Insert;

public interface CommentMapper {

    @Insert("INSERT INTO `fly_comment`(`id`,`uid`,`art_id`,`content`,`create_at`,`adopt`,`reply_id`)VALUES" +
            "(#{id},#{uid},#{artId},#{content},#{createAt},#{adopt},#{replyId})")
    void add(Comment comment);
}
