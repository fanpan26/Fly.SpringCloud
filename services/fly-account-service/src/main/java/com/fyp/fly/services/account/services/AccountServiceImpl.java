package com.fyp.fly.services.account.services;

import com.fyp.fly.common.result.api.JsonResult;
import com.fyp.fly.common.result.api.ResultUtils;
import com.fyp.fly.common.result.token.JwtVerifyResult;
import com.fyp.fly.common.tools.SafeEncoder;
import com.fyp.fly.services.account.domain.Account;
import com.fyp.fly.services.account.domain.JwtResult;
import com.fyp.fly.services.account.domain.SsoTicketResult;
import com.fyp.fly.services.account.repositories.mapper.AccountMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.fyp.fly.services.account.domain.Account.AccountType.NOT_EXISTS;
import static com.fyp.fly.services.account.domain.Account.AccountType.OFFLINE;
import static com.fyp.fly.services.account.domain.Account.AccountType.WRONG_PASSWORD;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    /**
     * ticket过期时间 60 秒
     */
    private static final int SSO_TICKET_EXPIRE = 60;

    /**
     *   登录信息过期时间 7天
     */
    private static final int LOGIN_STATUS_EXPIRE = 7;
    /**
     *   已经登录的用户缓存 KEY
     */
    private static final String SSO_LOGGED_USER = "sso:user";
    /**
     * ticket 缓存KEY
     * */
    private static final String SSO_TICKET_PREFIX = "sso:ticket:";
    /**
     * invalid ticket
     * */
    private static final String SSO_TICKET_INVALID = "invalid ticket";
    /**
     * 7 * 24 * 60 * 60
     * */
    private static final int SSO_TOKEN_EXPIRE_TIMESTAMP = 86400 * LOGIN_STATUS_EXPIRE;

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
            setUserLoginStatus(account.getId(),true);
            return createTicketResult(account.getId());
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
        if (StringUtils.isEmpty(token)) {
            return ResultUtils.newResult(OFFLINE.getCode(), OFFLINE.getMsg());
        }
        JwtVerifyResult result = SafeEncoder.verifyToken(jwtSecret, token);
        Long userId = Long.valueOf(result.getResult().getSubject());
        if (result.isVerified()) {
            if (isLogged(userId)) {
                //create a new ticket
                String ticket = generateTicket(userId);
                return createTicketResult(ticket, token);
            }
        }else{
            //remove user login cache
            setUserLoginStatus(userId,false);
        }
        return ResultUtils.newResult(OFFLINE.getCode(), OFFLINE.getMsg());
    }

    @Override
    public JsonResult verifyTicket(String ticket) {
        if (StringUtils.isEmpty(ticket)) {
            return ResultUtils.failed(SSO_TICKET_INVALID);
        }
        String userId = ops().get(SSO_TICKET_PREFIX + ticket);
        if (StringUtils.isEmpty(userId)) {
            return ResultUtils.failed(SSO_TICKET_INVALID);
        }
        Long userIdLong = Long.valueOf(userId);
        if (isLogged(userIdLong)) {
            clearTicket(ticket);
            //这个token是给应用端的
            String token = createToken(userIdLong);
            return ResultUtils.success(JwtResult.tokenResult(token,SSO_TOKEN_EXPIRE_TIMESTAMP));
        }
        return ResultUtils.failed(SSO_TICKET_INVALID);
    }


    private JsonResult createTicketResult(Long userId){
        String ticket = generateTicket(userId);
        String token = createToken(userId);
        return createTicketResult(ticket,token);
    }
    private JsonResult createTicketResult(String ticket,String token){
        return ResultUtils.success(new SsoTicketResult(ticket, token));
    }
    /**
     * 重新创建一个ticket
     * */
    private String generateTicket(Long userId) {
        String ticket = UUID.randomUUID().toString();
        ops().set(SSO_TICKET_PREFIX + ticket, userId.toString(), SSO_TICKET_EXPIRE, TimeUnit.SECONDS);
        if (logger.isDebugEnabled()) {
            logger.debug("generate ticket for ssokey:{},ticket:{}", userId, ticket);
        }
        return ticket;
    }

    private void clearTicket(String ticket) {
        ops().set(SSO_TICKET_PREFIX + ticket, "0", 1, TimeUnit.MILLISECONDS);
    }

    /**
     * 登录成功之后，写入 登录信息，并且新建一个ticket 返回
     */
    private void setUserLoginStatus(Long userId,boolean isLoggedIn) {
        //使用bit保存用户是否已经登录
        ops().setBit(SSO_LOGGED_USER, userId, isLoggedIn);
    }

    private boolean isLogged(Long userId){
        return ops().getBit(SSO_LOGGED_USER,userId);
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
