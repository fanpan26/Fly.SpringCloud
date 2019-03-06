package com.fyp.fly.services.account.services;

import com.fyp.fly.common.api.result.JsonResult;
import com.fyp.fly.common.api.result.ResultUtils;
import com.fyp.fly.services.account.domain.Account;
import com.fyp.fly.services.account.repositories.mapper.AccountMapper;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import static com.fyp.fly.services.account.domain.Account.ERROR_MSG_NOT_EXIST;
import static com.fyp.fly.services.account.domain.Account.ERROR_MSG_WRONG_PWD;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private AccountMapper accountRepository;

    @Override
    public JsonResult login(String loginName, String loginPwd) {
        ValueOperations<String, String> redis = redisTemplate.opsForValue();
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
