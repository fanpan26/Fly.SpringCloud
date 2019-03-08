package com.fyp.fly.common.result.api;

import java.io.Serializable;

/**
 * @author fyp
 * @crate 2019/3/8 23:30
 * @project fly
 */
public class SsoTicketApiResult implements Serializable {
    private String ticket;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
}