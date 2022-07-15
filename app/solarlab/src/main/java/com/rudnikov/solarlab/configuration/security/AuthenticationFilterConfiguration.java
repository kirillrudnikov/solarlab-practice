package com.rudnikov.solarlab.configuration.security;

import com.rudnikov.solarlab.entity.UserEntity;
import com.rudnikov.solarlab.entity.UserRole;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class AuthenticationFilterConfiguration extends AnonymousAuthenticationFilter {

    private final String key;
    private static final String USER_SESSION_KEY = "user";

    public AuthenticationFilterConfiguration(String key) {
        super(key);
        this.key = key;
    }

    @Override
    protected Authentication createAuthentication(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        UserEntity user = Optional.ofNullable((UserEntity) httpSession.getAttribute(USER_SESSION_KEY))
                .orElseGet(() -> {
                    UserEntity anonymous = new UserEntity();
                    anonymous.setUsername("anonymousUser");
                    httpSession.setAttribute(USER_SESSION_KEY, anonymous);
                    return anonymous;
                });
        return new AnonymousAuthenticationToken(key, user, AuthorityUtils.createAuthorityList(UserRole.ANONYMOUS.getAuthority()));
    }
}