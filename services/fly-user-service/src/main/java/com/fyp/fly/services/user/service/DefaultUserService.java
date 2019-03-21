package com.fyp.fly.services.user.service;

import com.alibaba.fastjson.JSON;
import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.common.utils.JSONUtils;
import com.fyp.fly.services.user.domain.User;
import com.fyp.fly.services.user.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author fyp
 * @crate 2019/3/13 20:13
 * @project fly
 */
@Service
public class DefaultUserService implements UserService {

    private static final String USER_CACHE_KEY = "service:user:";

    @Autowired
    private HashOperations<String, String, Object> hashOps;

    @Autowired
    private UserMapper userMapper;

    private User getUserFromCache(Long userId){
        User user;
        Object userObject = hashOps.get(USER_CACHE_KEY,userId.toString());
        if (userObject == null){
           user = userMapper.getUserById(userId);
           hashOps.put(USER_CACHE_KEY,userId.toString(), JSONUtils.toJSONString(user));
        }else {
            user = JSONUtils.parseObject((String)userObject,User.class);
        }
        return user;
    }

    @Override
    public JsonResult getUserInfo(Long userId) {
        User user = getUserFromCache(userId);
        return ResultUtils.success(user);
    }

    @Override
    public JsonResult getUserList(List<String> userIds) {
        List<Object> users = hashOps.multiGet(USER_CACHE_KEY, userIds);
        List<User> usersList = new ArrayList<>(users.size());
        for (Object user : users){
            if (user != null){
                usersList.add(JSONUtils.parseObject(user.toString(),User.class));
            }
        }
        return ResultUtils.success(usersList);
    }
}