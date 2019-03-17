package com.fyp.fly.services.user.service;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.common.utils.JSONUtils;
import com.fyp.fly.services.user.cache.Cache;
import com.fyp.fly.services.user.domain.User;
import com.fyp.fly.services.user.repository.mapper.UserMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author fyp
 * @crate 2019/3/13 20:13
 * @project fly
 */
@Service
public class DefaultUserService implements UserService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private ValueOperations<String, String> ops() {
        return redisTemplate.opsForValue();
    }

    @Autowired
    private UserMapper userMapper;


    private User getUserFromCache(Long userId){
        User user;
        String userJson = ops().get(Cache.CACHE_USER+userId);
        if (StringUtils.isEmpty(userJson)){
           user = userMapper.getUserById(userId);
           ops().set(Cache.CACHE_USER+userId,JSONUtils.toJSONString(user),Cache.CACHE_USER_EXPIRE, TimeUnit.SECONDS);
        }else{
            user = JSONUtils.parseObject(userJson,User.class);
        }
        return user;
    }

    @Override
    public JsonResult getUserInfo(Long userId) {
        User user = getUserFromCache(userId);
        return ResultUtils.success(user);
    }
}