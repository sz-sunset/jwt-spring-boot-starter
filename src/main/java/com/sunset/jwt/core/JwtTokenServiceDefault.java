package com.sunset.jwt.core;

import com.sunset.jwt.configuration.JWTProperties;
import com.sunset.jwt.configuration.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: jik.shu
 * @since: 2020-01-07
 */
@Slf4j
public class JwtTokenServiceDefault implements JwtTokenService {

    protected JWTProperties jwtProperties;

    protected List<Validator<Claims>> validators;

    public JwtTokenServiceDefault(JWTProperties jwtProperties, List<Validator<Claims>> validators) {
        this.jwtProperties = jwtProperties;
        this.validators = validators;
    }

    public JwtUser parseToken(String token) {
        Claims body = getClaims(token);
        return new JwtUser(String.valueOf(body.get("userId")), body.getSubject(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public String generateToken(String userName, String userId) {
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put("userId", userId);
        Date expiration = Date.from(ZonedDateTime.now().plusSeconds(jwtProperties.getExp()).toInstant());
        claims.setExpiration(expiration);
        JwtBuilder jwtBuilder = Jwts.builder().setClaims(claims);
        jwtBuilder.signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret());
        return jwtBuilder.compact();
    }


    @Override
    public ValidationResult validate(String token) {
        try {
            Claims body = getClaims(token);
            for (Validator<Claims> validator : validators) {
                ValidationResult validationResult = validator.validate(body);
                if (validationResult != null) {
                    return validationResult;
                }
            }
        } catch (Exception e) {
            log.error("jwt parse error :{}", e.getMessage());
            return ValidationResult.error();
        }
        return ValidationResult.success();
    }

    private Claims getClaims(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            log.error("jwt parse error :{}", e.getMessage());
            return null;
        }
    }
}