package com.sunset.jwt.core;

import com.sunset.jwt.exception.JWTException;
import io.jsonwebtoken.Claims;

/**
 * Token 签名验证器
 *
 * @author jik.shu
 * @since 2012-01-06
 */
public class ValidatorSignature implements Validator<Claims> {

    @Override
    public ValidationResult validate(Claims claims) throws JWTException {
        return null == claims ? ValidationResult.error() : null;
    }
}