package com.nashtech.ecommerce_webapp.security;

import com.nashtech.ecommerce_webapp.exceptions.CustomException;
import com.nashtech.ecommerce_webapp.models.user.Role;
import com.nashtech.ecommerce_webapp.models.user.RoleName;
import com.nashtech.ecommerce_webapp.models.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private  String secretKey = "secret-key";
    private  Long expireLength = 1200000L;

    @Autowired
    private MyUserDetails myUserDetails;

    @PostConstruct
    protected void init(){
        secretKey = Base64Utils.encodeToString(secretKey.getBytes());
    }

    //Create jwt token
    public String createToken(Authentication authObj){
        UserDetailsImpl userDetails = (UserDetailsImpl) authObj.getPrincipal();

        //Subject for jwt claims
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());

        claims.put("auth", userDetails.getAuthorities());

        Date now = new Date();
        Date availableDuration = new Date(now.getTime() + expireLength);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(availableDuration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //Get username from jwt token (claims)
    public String getUsername(String token){
        //email as username
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }


    //Get authentication object from jwt Token
    public Authentication getAuthentication(String token){
        String username = getUsername(token);
        UserDetails userDetails = myUserDetails.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities() );
    }

    //Resolve Jwt token from request header
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    //Validate Jwt token
    public boolean isJwtTokenValid(String token){
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }
        catch (JwtException | IllegalArgumentException ex){
            throw new CustomException("Expired or invalid JWT Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
