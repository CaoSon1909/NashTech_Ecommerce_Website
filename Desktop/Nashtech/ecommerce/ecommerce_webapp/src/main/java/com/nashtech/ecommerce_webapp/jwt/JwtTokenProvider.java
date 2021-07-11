package com.nashtech.ecommerce_webapp.jwt;

import com.nashtech.ecommerce_webapp.jwt.details.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "sonpc1909";

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 604800000L;

    // Tạo ra jwt từ thông tin user
    public String generateToken(Authentication authentication) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        //Lấy ra UserDetails object bên trong authentication object
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        // Tạo chuỗi json web token từ email của user.
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }


    public String getUsernameFromJWT(String token){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException | SignatureException ex) {
            log.error("Invalid JWT token" + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token"+ ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token"+ ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty."+ ex.getMessage());
        }
        return false;
    }
}
