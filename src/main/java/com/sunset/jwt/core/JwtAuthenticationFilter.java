package com.sunset.jwt.core;

import com.sunset.jwt.configuration.JWTProperties;
import com.sunset.jwt.exception.JWTException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * jwt认证过滤器
 *
 * @author: jik.shu
 * @since: 2020-01-07
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected JWTProperties jwtProperties;
    protected JwtTokenService jwtTokenService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JWTProperties jwtProperties, JwtTokenService jwtTokenService) {
        super("/**");
        super.setAuthenticationManager(authenticationManager);
        this.jwtProperties = jwtProperties;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println(request.getRequestURL());
        String token = getJwtToken(request);
        if (token == null) {
            throw new AuthenticationServiceException("No JWT token found in request headers or cookie");
        }
        ValidationResult validate = jwtTokenService.validate(token);

        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(token);
        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    /**
     * 从header获取token
     *
     * @param request
     * @return
     */
    private String getJwtToken(HttpServletRequest request) {
        String header = request.getHeader(jwtProperties.getAuthorizationHeaderName());
        String authToken;
        if (header != null && header.startsWith(jwtProperties.getAuthorizationHeaderValuePrefix())) {
            authToken = header.substring(7);
        } else {
            authToken = getCookie(request.getCookies(), jwtProperties.getAuthorizationCookieName());
        }
        return authToken;
    }

    /**
     * 从cookie获取jwt
     *
     * @param name
     * @return
     */
    private String getCookie(Cookie[] cookies, String name) {
        try {
            for (int i = 0; i < (cookies == null ? 0 : cookies.length); i++) {
                if ((name).equalsIgnoreCase(cookies[i].getName())) {
                    return URLDecoder.decode(cookies[i].getValue(), "UTF-8");
                }
            }
        } catch (Exception e) {
            logger.info("--------从cookie获取token失败--------", e);
        }
        return null;
    }
}