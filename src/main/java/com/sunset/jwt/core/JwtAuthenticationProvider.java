package com.sunset.jwt.core;

import com.sunset.jwt.configuration.JwtUser;
import com.sunset.jwt.exception.JWTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final JwtTokenService jwtTokenService;

    public JwtAuthenticationProvider(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = jwtAuthenticationToken.getToken();
        if (null == token || token.length() <= 0) {
            throw new JWTException("JWT token is null");
        }
        JwtUser jwtUser = jwtTokenService.parseToken(token);
        if (jwtUser == null) {
            throw new JWTException("JWT token is not valid");
        }
        return jwtUser;
    }
}