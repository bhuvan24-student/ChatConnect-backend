package com.chatconnect.Security.Jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;
    private SecretKey secretKey;
    @PostConstruct
    public void init(){
        byte[] bytes = Decoders.BASE64URL.decode(secret);
        secretKey = Keys.hmacShaKeyFor(bytes);
    }
    public String createtoken(String email){
        String token= Jwts.builder()
                .subject(email)
                .signWith(secretKey)
                .compact();
        return token;
    }
    public boolean validatetoken(String token){
        try{
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    public String extractemail(String Token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(Token)
                .getPayload()
                .getSubject();
    }
}
