package com.fyp.fly.services.account.domain;


import org.springframework.util.StringUtils;

import java.io.Serializable;

public class SsoTicketResult implements Serializable {

    public static final SsoTicketResult SHOULD_LOGIN_RESULT = new SsoTicketResult();

    private String ticket;
    private String token;

    public SsoTicketResult(String ticket,String token) {
        if(StringUtils.isEmpty(ticket)){
            throw new IllegalArgumentException("ticket must not be null or empty");
        }
        this.ticket = ticket;
        this.token = token;
    }

    public SsoTicketResult() {

    }

    public String getTicket(){
        return ticket;
    }

    public String getToken(){
        return token;
    }

    public boolean isLoggedIn(){
        return !StringUtils.isEmpty(token);
    }
}
