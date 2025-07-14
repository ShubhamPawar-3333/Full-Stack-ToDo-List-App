package com.portfolio.todolist.ToDoListApplication.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Utility class for JWT token generation and validation.
 */
@Component
public class JwtTokenProvider {

//    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//    private final long validityInMilliseconds = 3600000; // 1 hour
    private final Key key;
    private final long validityInMilliseconds;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
                            @Value("${jwt.expiration:3600000}") long validityInMilliseconds){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    /**
     * Generates a JWT token for a user.
     *
     * @param username The user's username.
     * @return The generated JWT token.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(key)
                .compact();
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token The JWT token.
     * @return The username.
     */
    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    /**
     * Validates a JWT token.
     *
     * @param token The JWT token.
     * @return True if valid, false otherwise.
     */
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
