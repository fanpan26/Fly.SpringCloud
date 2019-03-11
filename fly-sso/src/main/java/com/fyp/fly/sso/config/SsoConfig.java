package com.fyp.fly.sso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author fyp
 * @crate 2019/3/11 22:12
 * @project fly
 */
@Configuration
public class SsoConfig {

    private static String flyWebHost;

    @Value("${fly.sso.redirect_url.fly_admin}")
    private static String flyAdminHost;

    @Value("${fly.sso.redirect_url.fly_web}")
    public void setFlyWebHost(String flyWebHost){
        SsoConfig.flyWebHost = flyWebHost;
    }

    @Value("${fly.sso.redirect_url.fly_admin}")
    public void setFlyAdminHost(String flyAdminHost){
        SsoConfig.flyAdminHost = flyAdminHost;
    }

    public static String getUrl(String from){
        if(StringUtils.isEmpty(from)){
            return flyWebHost;
        }
        switch (from.toLowerCase()){
            case "fly-web":
                return flyWebHost;
            case "fly-admin":
                return flyAdminHost;
        }
        return flyWebHost;
    }
}
