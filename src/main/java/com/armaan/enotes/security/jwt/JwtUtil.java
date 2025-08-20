package com.armaan.enotes.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private  String SECRET_KEY;
    private final long ACCESS_EXPIRATION = 1000 * 60 * 5; // 5 minutes
    public final long REFRESH_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 days


    private Key getSigningKey(){
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, REFRESH_EXPIRATION);
    }

    public String generateAccessToken(String username) {
    return generateToken(username, ACCESS_EXPIRATION);
}

    public String generateToken(String username,Long expiration)
    {
        return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+expiration))
        .signWith(getSigningKey(),SignatureAlgorithm.HS256)
        .compact();
    }

    public String extractUsername(String token){
        Claims claims= extractAllClaims(token);
            return claims.getSubject();
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }

    public boolean validateToken(String token,UserDetails userDetails)
    {
        String extractedUserName=extractUsername(token);
        return ( extractedUserName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    private boolean isTokenExpired(String token) {
        Date expiration=extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }


    public <T> T extractClaim(String token,Function<Claims,T> claimResolver)
    {
        final Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);
    }

}
