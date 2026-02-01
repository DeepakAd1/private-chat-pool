package com.chat.privatepool.util;

import com.chat.privatepool.object.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret:}")
    private String secretKey;

    @Value("${jwt.expiration:}") // e.g., 86400000 ms = 1 day
    private long expiration;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("userType", user.getUserType());
        claims.put("guestToken", user.getGuestToken());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getDefaultName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        if (CommonUtil.isEmptyStr(token)) return false;
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    public String extractToken(ServerHttpRequest request) {

        URI uri = request.getURI();
        String query = uri.getQuery(); // token=xxx

        if (query == null) {
            return null;
        }

        for (String param : query.split("&")) {
            if (param.startsWith("token=")) {
                return param.substring(6);
            }
        }

        return null;
    }


    public Long extractUserId(String token) {
        if (CommonUtil.isEmptyStr(token)) return 0L;
        return extractAllClaims(token).get("userId", Long.class);
    }

    public String extractUserType(String token) {
        if (CommonUtil.isEmptyStr(token)) return null;
        return extractAllClaims(token).get("userType", String.class);
    }

    public String extractDisplayName(String token) {
        if (CommonUtil.isEmptyStr(token)) return null;
        return extractAllClaims(token).getSubject();
    }
}
