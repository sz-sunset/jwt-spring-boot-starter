package com.sunset.jwt.core;

import com.sunset.jwt.exception.JWTException;

/**
 * JWT Token验证器
 *
 * @author: jik.shu
 * @since: 2020-01-07
 */
public interface Validator<T> {
    ValidationResult validate(T t) throws JWTException;
}