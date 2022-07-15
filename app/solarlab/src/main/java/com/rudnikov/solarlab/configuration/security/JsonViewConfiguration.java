package com.rudnikov.solarlab.configuration.security;

import com.rudnikov.solarlab.model.JsonViewClass;
import com.rudnikov.solarlab.entity.UserRole;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.util.Collection;

@ControllerAdvice
public class JsonViewConfiguration extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer,
                                           MediaType contentType,
                                           MethodParameter returnType,
                                           ServerHttpRequest request,
                                           ServerHttpResponse response
    ) {
        Class<?> viewClass = JsonViewClass.Anonymous.class;

        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getAuthorities() != null) {
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

            if (authorities.stream().anyMatch(o -> o.getAuthority().equals(UserRole.USER.getAuthority()))) {
                viewClass = JsonViewClass.User.class;
            }
            if (authorities.stream().anyMatch(o -> o.getAuthority().equals(UserRole.ADMINISTRATOR.getAuthority()))) {
                viewClass = JsonViewClass.Administrator.class;
            }
        }
        bodyContainer.setSerializationView(viewClass);
    }

}