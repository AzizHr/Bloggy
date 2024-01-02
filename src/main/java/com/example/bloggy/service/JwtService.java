package com.example.bloggy.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public interface JwtService {

    public String extractEmail(String token);
    public String generateToken(UserDetails userDetails);
    public String createToken(Map<String, Object> claims, String subject);
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    public Claims extractAllClaims(String token);
    public Boolean isTokenValid(String token, UserDetails userDetails);
    public Date extractExpiration(String token);
    public Boolean isTokenExpired(String token);
    public Key getSignInKey();

}
