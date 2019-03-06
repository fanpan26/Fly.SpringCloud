package com.fyp.fly.services.account.services;

import com.fyp.fly.common.api.result.JsonResult;
import com.fyp.fly.common.api.result.ResultUtils;
import com.fyp.fly.common.tools.SafeEncoder;
import com.fyp.fly.services.account.domain.Account;
import com.fyp.fly.services.account.domain.SsoTicketResult;
import com.fyp.fly.services.account.repositories.mapper.AccountMapper;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.Date;
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

    @Value("${jwt.secret}")
    private String jwtSecret;

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
            String ticket = createTicket(account.getId());
            String token = createToken(account.getId());
            return createTicketResult(ticket,token);
        }
        return ResultUtils.newResult(WRONG_PASSWORD.getCode(),WRONG_PASSWORD.getMsg());
    }

    /**
     * 根据JWT来校验是否登录，并返回应用端的ticket
     *
     * @param token jwtToken
     */
    @Override
    public JsonResult generateTicket(String token) {
        return null;
    }


    private JsonResult createTicketResult(String ticket,String token){
        return ResultUtils.success(new SsoTicketResult(ticket, token));
    }
    /**
     * 重新创建一个ticket
     * */
    private String generateTicket(Long userId) {
        String ticket = UUID.randomUUID().toString();
        ops().set("sso:ticket:" + ticket, userId.toString(), LOGIN_TICKET_EXPIRE, TimeUnit.SECONDS);
        if (logger.isDebugEnabled()) {
            logger.debug("generate ticket for ssokey:{},ticket:{}", userId, ticket);
        }
        return ticket;
    }

    /**
     * 登录成功之后，写入 登录信息，并且新建一个ticket 返回
     */
    private String createTicket(Long userId) {
        //使用bit保存用户是否已经登录
        ops().setBit("sso:user", userId, true);
        return generateTicket(userId);
    }
    /**
     * 创建 jwtToken
     * */
    private String createToken(Long userId) {
        Date expireDate = DateUtils.addDays(new Date(), LOGIN_STATUS_EXPIRE);
        String token = SafeEncoder.jwtToken(jwtSecret, expireDate, userId, "fly-web", "fly-admin");
        return token;
    }
}
