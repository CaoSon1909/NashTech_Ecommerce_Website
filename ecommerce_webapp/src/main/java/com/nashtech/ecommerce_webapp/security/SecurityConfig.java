package com.nashtech.ecommerce_webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MyUserDetails myUserDetails;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //Disable CSRF (cross site request forgery)
        http.csrf().disable();

        //No session will be created or use by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //Entry points
        http.authorizeRequests()
                .antMatchers("/public/signin").permitAll()//
                .antMatchers("/public/signup").permitAll()//
                .antMatchers("/public/user").permitAll()
                .antMatchers("/api/v1/users").permitAll()
                .antMatchers("/api/v1/public/categories").permitAll()//
                .antMatchers("/api/v1/public/brands").permitAll()//
                .antMatchers("/api/v1/vehicles/all").permitAll()
                .antMatchers("/api/v1/vehicles/details").permitAll()
                .anyRequest().authenticated();

        //If user try to access a resource without having enough permissions
        http.exceptionHandling().accessDeniedPage("/login");

        //Apply JWT
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider, myUserDetails));
        http.cors();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
