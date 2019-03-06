package com.fyp.fly.sso.api.results;

import lombok.Data;

import java.io.Serializable;

@Data
public class SsoTicketApiResult implements Serializable {
    private String ticket;
    private long expireAt;
    private boolean isLoggedIn;
}