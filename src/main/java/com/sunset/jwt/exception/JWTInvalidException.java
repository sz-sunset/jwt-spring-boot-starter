package com.sunset.jwt.exception;

/**
 * @author jik.shu
 * @since 2012-01-06
 */
public class JWTInvalidException extends JWTException {
    private final static String message = "token is invalid!";
    public JWTInvalidException() {
        super(message);
    }
}
