package com.fyp.fly.services.user.repository.mapper;

import com.fyp.fly.services.user.domain.FlyUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author fyp
 * @crate 2019/3/13 19:51
 * @project fly
 */
public interface UserMapper {
    /**
     * 获取基础用户信息
     * */
    @Select("select id, name,avatar,sex,vip,auth,address,sign,auth_str,create_at from fly_user where id=#{id}")
    @Results({
            @Result(property = "authStr",  column = "auth_str"),
            @Result(property = "createAt",column="create_at")
    })
    FlyUser getUserById(@Param("id") Long id);
}
