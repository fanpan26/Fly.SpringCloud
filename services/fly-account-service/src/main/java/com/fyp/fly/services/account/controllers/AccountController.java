package com.fyp.fly.services.account.controllers;

import com.fyp.fly.common.api.result.JsonResult;
import com.fyp.fly.common.api.result.ResultUtils;
import com.fyp.fly.services.account.domain.Account;
import com.fyp.fly.services.account.repositories.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.fyp.fly.services.account.domain.Account.ERROR_MSG_NOT_EXIST;
import static com.fyp.fly.services.account.domain.Account.ERROR_MSG_WRONG_PWD;

/**
 * @author fyp
 * @crate 2019/3/5 21:50
 * @project fly
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountMapper accountRepository;

    /**
     * @param loginName 用户名
     * @param loginPwd  用户密码
     * @desc 用户登录接口
     * @return userId or error msg
     * @author fyp
     * */
    @PostMapping("/login")
    public JsonResult login(@RequestParam("name") String loginName, @RequestParam("pwd") String loginPwd) {
        Account account = accountRepository.loginByName(loginName);
        if (Account.notExists(account)) {
            return ResultUtils.failed(ERROR_MSG_NOT_EXIST);
        }
        if (account.isCorrectPassword(loginPwd)) {
            return ResultUtils.success(account.getId());
        }
        return ResultUtils.failed(ERROR_MSG_WRONG_PWD);
    }
}
