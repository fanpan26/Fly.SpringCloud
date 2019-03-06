package com.fyp.fly.common.result.token;

import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author fyp
 * @crate 2019/3/6 19:50
 * @project fly
 */
public class JwtVerifyResult {
    private Exception exception;
    private DecodedJWT result;

    public JwtVerifyResult(Exception ex,DecodedJWT result){
        this.exception = ex;
        this.result = result;
    }

    public Exception getException() {
         return exception;
    }

    public DecodedJWT getResult() {
        return result;
    }

    public boolean isVerified(){
        return exception == null;
    }
}
