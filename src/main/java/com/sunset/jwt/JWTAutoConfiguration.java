package com.sunset.jwt;

import com.sunset.jwt.configuration.JWTProperties;
import com.sunset.jwt.core.*;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContext;

import java.util.Arrays;

/**
 * jwt自动配置
 *
 * @author jik.shu
 * @since 2012-01-06
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ConditionalOnClass({Jwts.class, SecurityContext.class})
@EnableConfigurationProperties(JWTProperties.class)
@Import(DefaultJWTSecurityAutoConfiguration.class)
public class JWTAutoConfiguration {

    @Autowired
    JWTProperties jwtProperties;

    public JWTAutoConfiguration() {
        log.info("-----------JWT auto config-------------");
    }

    @ConditionalOnMissingBean(JwtTokenService.class)
    @Bean
    JwtTokenService jwtTokenService() {
        return new JwtTokenServiceDefault(jwtProperties, Arrays.asList(new ValidatorSignature(), new ValidatorExpired()));
    }

    /**
     * 认证提供者
     *
     * @return
     */
    @ConditionalOnMissingBean(JwtAuthenticationProvider.class)
    @Bean
    JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtTokenService());
    }
}