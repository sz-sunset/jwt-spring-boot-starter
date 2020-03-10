package com.sunset.jwt.configuration;

import com.sunset.jwt.core.JwtAuthenticationFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * jwt配置属性
 *
 * @author jik.shu
 * @since 2012-01-06
 */
@Getter
@Setter
@ConfigurationProperties("sunset.jwt")
public class JWTProperties {

    /**
     * secret of jwt
     */
    private String secret = "sunset_jwt";

    /**
     * long expiration(minute).Client must be authorized when token is invalid within exp.
     */
    private long exp = 10080;

    /**
     * header name got by {@link JwtAuthenticationFilter} to get token
     */
    private String authorizationHeaderName = "Authorization";

    /**
     * cookie name got by {@link JwtAuthenticationFilter} to get token
     */
    private String authorizationCookieName = "Jwt-Token";

    /**
     * prefix of header value dealt by {@link JwtAuthenticationFilter}
     */
    private String authorizationHeaderValuePrefix = "Bearer ";

    public enum ValidationState {
        SUCCESS, EXPIRED, ERROR
    }
}