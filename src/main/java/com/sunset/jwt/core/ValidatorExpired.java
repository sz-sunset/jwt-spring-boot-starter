package com.sunset.jwt.core;

import com.sunset.jwt.exception.JWTException;
import io.jsonwebtoken.Claims;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Token失效验证器
 *
 * @author jik.shu
 * @since 2012-01-06
 */
public class ValidatorExpired implements Validator<Claims> {
    @Override
    public ValidationResult validate(Claims claims) throws JWTException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expire = LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());

        return now.isAfter(expire) ? ValidationResult.expired() : null;
    }
}