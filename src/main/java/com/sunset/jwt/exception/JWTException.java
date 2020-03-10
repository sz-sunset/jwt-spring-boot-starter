package com.sunset.jwt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author jik.shu
 * @since 2012-01-06
 */
public class JWTException extends AuthenticationException {
    public JWTException(String message) {
        super(message);
    }
}