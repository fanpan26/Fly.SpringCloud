package com.fyp.fly.services.account.services;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

/**
 * @author fyp
 * @crate 2019/3/7 21:56
 * @project fly
 */
@Service
public class DefaultPasswordHashing implements PasswordHashing {
    @Override
    public String hash(String name,String password) {
        String value = name.substring(0, 3) + password + password.substring(0, 3);
        String hash = Hashing.sha256().hashBytes(value.getBytes()).toString();
        return Hashing.sha256().hashBytes(hash.getBytes()).toString();
    }
}
