package com.teste.webfluxjwt.util;

import com.teste.webfluxjwt.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil implements Serializable {
    private static final long serialVersionUID = 1L;

    @Value("${springbootwebfluxjjwt.jjwt.secret}")
    private String secret;

    @Value("${springbootwebfluxjjwt.jjwt.expiration}")
    private String expirationTime;


    public Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String getUsernameFormToken(String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token){
        return getAllClaimsFromToken(token).getExpiration();
    }

    public Boolean isTokenExpired(String token){
        return getExpirationDateFromToken(token).before(new Date());
    }

    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRoles());
        claims.put("enable", user.getEnabled());
        return doGenerateToken(claims, user.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + Long.parseLong(expirationTime) * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token){
        return !isTokenExpired(token);
    }

}
