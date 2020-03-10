package com.sunset.jwt;

import com.sunset.jwt.configuration.JWTProperties;
import com.sunset.jwt.core.JwtAuthenticationFilter;
import com.sunset.jwt.core.JwtAuthenticationSuccessHandler;
import com.sunset.jwt.core.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * 默认Jwt security 配置类
 *
 * @author jik.shu
 * @since 2012-01-06
 */
@Slf4j
@Configuration
@ConditionalOnMissingBean(DefaultJWTSecurityAutoConfiguration.class)
public class DefaultJWTSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

    JWTProperties jwtProperties;
    JwtTokenService jwtTokenService;

    public DefaultJWTSecurityAutoConfiguration(JWTProperties jwtProperties, JwtTokenService jwtTokenService) {
        this.jwtProperties = jwtProperties;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected final void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), jwtProperties, jwtTokenService);
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);
    }
}