package com.sunset.jwt.core;

import com.sunset.jwt.configuration.JwtUser;

public interface JwtTokenService {

    /**
     * 解析Token
     *
     * @param token
     * @return
     */
    JwtUser parseToken(String token);

    /**
     * 生成Token
     *
     * @param userName
     * @param userId
     * @return
     */
    String generateToken(String userName, String userId);

    /**
     * token校验
     *
     * @param token
     * @return
     */
    ValidationResult validate(String token);
}