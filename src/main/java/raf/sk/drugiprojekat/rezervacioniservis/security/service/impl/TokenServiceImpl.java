package raf.sk.drugiprojekat.rezervacioniservis.security.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import raf.sk.drugiprojekat.rezervacioniservis.security.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${oauth.jwt.secret}")
    private String jwtSecret;

    @Override
    public String generate(Claims claims) {
        return Jwts.builder()
                .claims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    @Override
    public Claims parseToken(String jwt) {
        Claims claims;
        try {
            claims = (Claims) Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parse(jwt).getPayload();
        } catch (Exception e) {
            return null;
        }
        return claims;
    }
}
