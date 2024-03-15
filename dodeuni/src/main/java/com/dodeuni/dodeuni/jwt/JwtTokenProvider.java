package com.dodeuni.dodeuni.jwt;

import com.dodeuni.dodeuni.config.GlobalConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final GlobalConfig config;

    public String generateToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + config.getExpirationTime()))
                .signWith(SignatureAlgorithm.HS512, config.getSecretKey())
                .compact();
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader(AUTHORIZATION_HEADER, accessToken);
    }
}