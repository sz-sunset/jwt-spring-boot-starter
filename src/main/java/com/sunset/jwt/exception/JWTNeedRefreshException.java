package com.sunset.jwt.exception;

/**
 * @author jik.shu
 * @since 2012-01-06
 */
public class JWTNeedRefreshException extends JWTException {
    private final static String message = "token needs to be refreshed!";

    public JWTNeedRefreshException() {
        super(message);
    }
}
