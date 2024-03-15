package com.dodeuni.dodeuni.jwt;

import com.dodeuni.dodeuni.config.GlobalConfig;
import com.dodeuni.dodeuni.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final String secretKey;
    private final int expirationTime;

    public JwtTokenProvider(GlobalConfig config) {
        this.secretKey = config.getSecretKey();
        this.expirationTime = config.getExpirationTime() * 1000;
    }

    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("roles", user.getRole());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader(AUTHORIZATION_HEADER, accessToken);
    }
}