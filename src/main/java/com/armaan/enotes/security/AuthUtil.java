// package com.armaan.enotes.security;

// import java.nio.charset.StandardCharsets;

// import javax.crypto.SecretKey;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// import com.armaan.enotes.entity.User;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.security.Keys;
// @Component
// public class AuthUtil {

//     @Value("${jwt.secretKey}")
//     private String jwtSecretKey;


//     private SecretKey getSecretKey(){
//         return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
//     }

//     public String generateAcessToken(User user){

//         return Jwts.builder()
//         .setSubject(user.getUsername())
//         .claim("userId", user.getId().toString())
//         .setIssuedAt(new Date())
//         .setExpiration(new Date())
//         .signWith(getSecretKey())
//         .compact();
//     }
// }
