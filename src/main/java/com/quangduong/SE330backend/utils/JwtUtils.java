package com.quangduong.SE330backend.utils;

import com.quangduong.SE330backend.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    @Autowired
    private Environment environment;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + Long.parseLong(environment.getProperty("jwt.expiration")));
        return Jwts.builder().setSubject(email).setIssuedAt(now).setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("jwt.secret")).compact();
    }

    public String getEmailFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(environment.getProperty("jwt.secret"))
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(environment.getProperty("jwt.secret")).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }
}
