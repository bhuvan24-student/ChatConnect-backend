package com.chatconnect.Security.Jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Base64;
@Component
public class SecrectKeyGen {
    public String Keygen() {
        byte[] key = Jwts.SIG.HS256.key().build().getEncoded();
        String secret = Base64.getEncoder().encodeToString(key);
        return secret;
    }
}
