package com.fyp.fly.web.controllers.biz;

import com.fyp.fly.common.constants.Fly;
import com.fyp.fly.common.dto.FlyUserDto;
import com.fyp.fly.common.tools.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fyp
 * @crate 2019/3/13 22:08
 * @project fly
 */
public abstract class BaseController {
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Value("${sso.url}")
    private String ssoUrl;

    protected FlyUserDto getUser() {
        FlyUserDto userFromAttribute = (FlyUserDto) request.getAttribute(Fly.WEB_ATTRIBUTE_USER_KEY);
        if (userFromAttribute == null) {
            try {
                response.sendRedirect(ssoUrl + "?from=fly-web");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            final String key = Fly.WEB_CACHE_USER_KEY + userFromAttribute.getId();
            String userJSON = redisTemplate.opsForValue().get(key);
            if (userJSON != null) {
                return JSONUtils.parseObject(userJSON, FlyUserDto.class);
            }

        }
        return new FlyUserDto();
    }

    protected Long getUserId(){
       return getUser().getId();
    }
}
