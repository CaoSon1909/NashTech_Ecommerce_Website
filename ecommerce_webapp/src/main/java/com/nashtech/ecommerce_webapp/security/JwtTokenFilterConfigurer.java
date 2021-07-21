package com.nashtech.ecommerce_webapp.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenProvider jwtTokenProvider;
    private MyUserDetails myUserDetails;

    public JwtTokenFilterConfigurer(JwtTokenProvider jwtTokenProvider, MyUserDetails myUserDetails) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.myUserDetails = myUserDetails;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider, myUserDetails);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
