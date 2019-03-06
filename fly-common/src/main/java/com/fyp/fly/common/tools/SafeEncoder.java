package com.fyp.fly.common.tools;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

public final class SafeEncoder {

    private static final String CHAR_SET = "UTF-8";

    /**
     * URL 编码
     * @param url 需要编码的url
     * @return 返回编码后的结果
     * */
    public static String encodeUrl(String url) {
        try {
            return URLEncoder.encode(url, CHAR_SET);
        } catch (UnsupportedEncodingException ex) {
            return url;
        }
    }

    /**
     * 创建一个jwtToken
     * @param secret 秘钥
     * @param audience token适用人
     * @param businessId 业务ID
     * @param expireAt 过期时间
     * */
    public static String jwtToken(String secret, Date expireAt,long businessId,String... audience) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withExpiresAt(expireAt)
                    .withIssuedAt(new Date())
                    .withAudience(audience)
                    .withSubject(String.valueOf(businessId))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * 校验TOKEN
     * */
    public static DecodedJWT verifyToken(String secret,String token,String... audience) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .withAudience(audience)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (JWTVerificationException exception) {
            return  null;
        }
    }

    public static void main(String[] args) {
        String secret = "123456";
//        String token = jwtToken(secret, new Date(), 123456, "fly-web", "fly-admin");
//        System.out.println(token);

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiZmx5LXdlYiIsImZseS1hZG1pbiJdLCJzdWIiOiIxMjM0NTYiLCJpc3MiOiJhdXRoMCIsImV4cCI6MTU1MTg2MzA2NCwiaWF0IjoxNTUxODYzMDY0fQ.pJk5Mtve6gfEQes5QDH2zHjRV39m46mxVLVATsknw84";

        DecodedJWT result = verifyToken(secret, token, "fly-web", "fly-admin");
        Object res = result.getPayload();
    }
}
