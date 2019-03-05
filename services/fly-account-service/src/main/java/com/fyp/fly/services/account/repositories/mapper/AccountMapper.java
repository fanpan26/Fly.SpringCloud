package com.fyp.fly.services.account.repositories.mapper;

import com.fyp.fly.services.account.domain.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author fyp
 * @crate 2019/3/5 21:37
 * @project fly
 */
public interface AccountMapper {
    @Select("select id,login_name,login_pwd from fly_account where login_name=#{loginName}")
    @Results({
            @Result(property = "loginName",  column = "login_name"),
            @Result(property = "loginPwd", column = "login_pwd")
    })
    Account loginByName(@Param("loginName") String loginName);
}
