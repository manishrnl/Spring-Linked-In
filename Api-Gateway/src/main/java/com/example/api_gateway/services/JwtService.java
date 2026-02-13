package com.example.api_gateway.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }


    public String getUserIdFromToken(String token) {
        log.debug("Attempting to parse JWT token to extract User ID");

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            log.debug("JWT signature verified successfully. Subject: {}", claims.getSubject());

            // 1. Try custom claim
            Object userIdClaim = claims.get("X-User-Id");
            if (userIdClaim != null) {
                String userId = userIdClaim.toString();
                log.info("User ID [{}] successfully extracted from 'X-User-Id' claim", userId);
                return userId;
            }

            // 2. Fallback to Subject
            String subject = claims.getSubject();
            if (subject != null && subject.matches("\\d+")) {
                String userId =subject;
                log.info("Custom claim missing; User ID [{}] extracted from subject", userId);
                return userId;
            }

            log.warn("JWT is valid but contains no numeric User ID in 'X-User-Id' or subject");
            return null;

        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token format: {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("JWT signature validation failed: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during JWT parsing: {}", e.getMessage());
        }

        return null;
    }


    public List<String> getUserRoleFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("roles", List.class);
    }

}
