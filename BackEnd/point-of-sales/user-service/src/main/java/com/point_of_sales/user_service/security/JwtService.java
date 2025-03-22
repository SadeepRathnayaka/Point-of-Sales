package com.point_of_sales.user_service.security;

import com.point_of_sales.user_service.entity.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET;

    private static final long VALIDITY = TimeUnit.HOURS.toMillis(30);

    private SecretKey generateKey(){
        byte[] decodeKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodeKey);
    }

    public String generateToken(String username, UserRole role){

        Map<String, Object> claims = new HashMap<>();
        claims.put("Role", role.name());
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();
    }
}
