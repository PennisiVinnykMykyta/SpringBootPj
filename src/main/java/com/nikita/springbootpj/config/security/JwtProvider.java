package com.nikita.springbootpj.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtProvider {

    public static final long JWT_TOKEN_VALIDITY = 60*60*5;
    @Value("${security.secret-key}")
    private  String secretKey;

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public String generateToken(Map<String, Object> claims, String subject){
        return Jwts.builder().
                setClaims(claims).
                setSubject(subject).
                setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
    }

    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token,Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
