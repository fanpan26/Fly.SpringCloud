package com.fyp.fly.web.config;

import com.fyp.fly.common.constants.Fly;
import com.fyp.fly.common.dto.UserModel;
import com.fyp.fly.common.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fyp
 * @crate 2019/3/16 14:29
 * @project fly
 */
@Component
public class FlyContext {

    private static RedisTemplate<String,String> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String,String> redisTemplate){
        FlyContext.redisTemplate = redisTemplate;
    }

   public static HttpServletRequest getCurrentRequest(){
       return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
   }

    public static UserModel getUser() {
        UserModel userFromAttribute = (UserModel) getCurrentRequest().getAttribute(Fly.WEB_ATTRIBUTE_USER_KEY);
        if (userFromAttribute == null) {
            return null;
        } else {
            final String key = Fly.WEB_CACHE_USER_KEY + userFromAttribute.getId();
            String userJSON = redisTemplate.opsForValue().get(key);
            if (userJSON != null) {
                return JSONUtils.parseObject(userJSON, UserModel.class);
            }
        }
        return new UserModel();
    }

    public static Long getUserId() {
        UserModel userFromAttribute = (UserModel) getCurrentRequest().getAttribute(Fly.WEB_ATTRIBUTE_USER_KEY);
        if (userFromAttribute == null) {
            return 0L;
        }
        return userFromAttribute.getId();
    }
}
