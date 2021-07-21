package com.nashtech.ecommerce_webapp.security;

import com.nashtech.ecommerce_webapp.exceptions.CustomException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    private MyUserDetails myUserDetails;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, MyUserDetails myUserDetails) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.myUserDetails = myUserDetails;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //1: get jwt token from request header
        try{
            System.out.println("==========AuthFilter==========");
            String token = parseJwt(httpServletRequest);
            System.out.println("JWT Token: " + token);
            //2: is token exist and valid?
            if (token != null && jwtTokenProvider.isJwtTokenValid(token)){
                System.out.println("Token is valid");
                String username =jwtTokenProvider.getUsername(token);
                System.out.println("Username get from token: " + username);
                UserDetailsImpl userDetails = (UserDetailsImpl) myUserDetails.loadUserByUsername(username);
                System.out.println("UserDetails: "+userDetails.toString());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, "", userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (CustomException ex){
            System.out.println("Token is invalid");
            //jwt token is invalid => clear context
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        System.out.println("=============================");
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
