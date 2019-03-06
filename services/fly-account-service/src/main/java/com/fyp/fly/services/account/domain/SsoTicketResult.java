package com.fyp.fly.services.account.domain;


import org.springframework.util.StringUtils;

import java.io.Serializable;

public class SsoTicketResult implements Serializable {

    public static final SsoTicketResult SHOULD_LOGIN_RESULT = new SsoTicketResult();

    private String ticket;
    private long expireAt;
    private boolean isLoggedIn;

    public SsoTicketResult(String ticket,long expires) {
        if(StringUtils.isEmpty(ticket)){
            throw new IllegalArgumentException("ticket must not be null or empty");
        }
        this.ticket = ticket;
        this.expireAt = expires;
        this.isLoggedIn = true;
    }

    public SsoTicketResult() {
        this.ticket = null;
        this.expireAt = -1;
        this.isLoggedIn = false;
    }

    public String getTicket(){
        return ticket;
    }

    public long getExpireAt(){
        return expireAt;
    }

    public boolean isLoggedIn(){
        return isLoggedIn;
    }
}
