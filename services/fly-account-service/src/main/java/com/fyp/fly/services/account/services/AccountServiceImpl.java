package com.fyp.fly.services.account.services;

import com.fyp.fly.common.api.result.JsonResult;
import com.fyp.fly.common.api.result.ResultUtils;
import com.fyp.fly.services.account.domain.Account;
import com.fyp.fly.services.account.domain.SsoTicketResult;
import com.fyp.fly.services.account.repositories.mapper.AccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.fyp.fly.services.account.domain.Account.AccountType.NOT_EXISTS;
import static com.fyp.fly.services.account.domain.Account.AccountType.WRONG_PASSWORD;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    //ticket过期时间 60 秒
    private static final int LOGIN_TICKET_EXPIRE = 60;
    //登录信息过期时间 7天
    private static final int LOGIN_STATUS_EXPIRE = 7;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private AccountMapper accountRepository;

    private ValueOperations<String, String> ops() {
        return redisTemplate.opsForValue();
    }
    @Override
    public JsonResult login(String loginName, String loginPwd) {
        Account account = accountRepository.loginByName(loginName);
        if(logger.isDebugEnabled()){
            logger.debug("login result:{}",account);
        }
        if (Account.notExists(account)) {
            return ResultUtils.newResult(NOT_EXISTS.getCode(), NOT_EXISTS.getMsg());
        }
        if (account.isCorrectPassword(loginPwd)) {
            String ticket = afterLoginSuccess(account.getId());
            return createTicketResult(ticket);
        }
        return ResultUtils.newResult(WRONG_PASSWORD.getCode(),WRONG_PASSWORD.getMsg());
    }

    @Override
    public JsonResult verifyTicket(String ssoKey) {
        // verify session
        String loginInfo = ops().get("sso:" + ssoKey);
        if (StringUtils.isEmpty(loginInfo)) {
            return ResultUtils.success(SsoTicketResult.SHOULD_LOGIN_RESULT);
        }

        String ticket = generateTicket(ssoKey);
        return createTicketResult(ticket);
    }

    private JsonResult createTicketResult(String ticket){
        return ResultUtils.success(new SsoTicketResult(ticket, System.currentTimeMillis() / 1000 + LOGIN_TICKET_EXPIRE));
    }
    /**
     * 重新创建一个ticket
     * */
    private String generateTicket(String ssoKey) {
        String ticket = UUID.randomUUID().toString();
        ops().set("sso:ticket:" + ticket, ssoKey, LOGIN_TICKET_EXPIRE, TimeUnit.SECONDS);
        if (logger.isDebugEnabled()) {
            logger.debug("generate ticket for ssokey:{},ticket:{}", ssoKey, ticket);
        }
        return ticket;
    }

    /**
     * 登录成功之后，写入 登录信息，并且新建一个ticket 返回
     */
    private String afterLoginSuccess(Long userId) {
        String ssoKey = UUID.randomUUID().toString();
        ops().set("sso:" + ssoKey, userId.toString(), LOGIN_STATUS_EXPIRE, TimeUnit.DAYS);
        if (logger.isDebugEnabled()) {
            logger.debug("save login status:ssoKey:{},value:{}", ssoKey, userId.toString());
        }
        return generateTicket(ssoKey);
    }
}
