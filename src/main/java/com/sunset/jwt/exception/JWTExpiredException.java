package com.sunset.jwt.exception;

/**
 * @author jik.shu
 * @since 2012-01-06
 */
public class JWTExpiredException extends JWTException {
    private final static String message = "token is expired!";
    public JWTExpiredException() {
        super(message);
    }
}
