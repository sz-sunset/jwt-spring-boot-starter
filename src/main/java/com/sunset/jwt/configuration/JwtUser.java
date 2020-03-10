package com.sunset.jwt.configuration;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * jwt用户实体
 *
 * @author: jik.shu
 * @since: 2020-01-07
 */
@Getter
public class JwtUser extends User {

    private String userId;

    public JwtUser(String userId, String userName, Collection<? extends GrantedAuthority> authorities) {
        super(userName, userId, authorities);
        this.userId = userId;
    }
}