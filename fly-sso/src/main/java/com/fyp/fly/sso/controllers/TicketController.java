package com.fyp.fly.sso.controllers;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.SsoTicketApiResult;
import com.fyp.fly.sso.api.client.AccountApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fyp
 * @crate 2019/3/8 23:06
 * @project fly
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    AccountApiClient accountApiClient;

    @GetMapping("/verify")
    public JsonResult<SsoTicketApiResult> verify(String ticket) {
        return accountApiClient.verifyTicket(ticket);
    }
}
