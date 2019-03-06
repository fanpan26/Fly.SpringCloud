package com.fyp.fly.services.account.services;

import com.fyp.fly.common.api.result.JsonResult;

public interface AccountService {
    JsonResult login(String loginName, String loginPwd);
}
