package com.rudnikov.solarlab.configuration.security;

import com.rudnikov.solarlab.entity.UserRole;
import com.rudnikov.solarlab.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        authenticationProvider.setUserDetailsService(userService);

        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(UserRole.USER.name(), UserRole.ADMINISTRATOR.name())
                .antMatchers(HttpMethod.POST, "/api/**").hasRole(UserRole.ADMINISTRATOR.name())
                .antMatchers(HttpMethod.PUT, "/api/**").hasRole(UserRole.ADMINISTRATOR.name())
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole(UserRole.USER.name(), UserRole.ADMINISTRATOR.name())
                //.antMatchers(HttpMethod.PATCH, "/api/**").hasAnyRole(UserRole.USER.name(), UserRole.ADMINISTRATOR.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

}