package com.chatconnect.Security.Jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;
    private SecretKey secretKey;
    public void init(){
        byte[] bytes = Decoders.BASE64.decode(secret);
        secretKey = Keys.hmacShaKeyFor(bytes);
    }
    public String createtoken(String email){
        String token= Jwts.builder()
                .subject(email)
                .signWith(secretKey)
                .compact();
        return token;
    }
}
