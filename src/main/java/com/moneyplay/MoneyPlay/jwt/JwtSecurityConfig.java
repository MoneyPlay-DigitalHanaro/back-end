package com.moneyplay.MoneyPlay.jwt;

import com.moneyplay.MoneyPlay.repository.UserRepository;
import com.moneyplay.MoneyPlay.service.UserService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private UserRepository userRepository;
    public JwtSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(
                new JwtRequestFilter(userRepository),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}