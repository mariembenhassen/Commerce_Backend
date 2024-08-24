package com.magazin.commerce.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    public static final String SECRET="cxd8+rn4lIWl3XHGUsR5q8sbXk04BYKtPakDhd4CGYYVcdantBGMYCoZj5zvBlehJVBrh52vsnL5L/1B6FMhklMX2McIPv/Sz6I/e2cRQlXYlxgcymQGb8X+Fvz43rU8+/SPXsafX9qeTL8HeNlzVY4lLSKS4dcPdKE7dlWXrTWkrazKVXMqFP7Xt3bs4dIK7YKfARJyPRPEpEf+XzADf4qlmwvEwlOpsN/KnUmZXfbEEvroptPZ2rSmWaSpl0MD5gCbYcrV89Et4huEq9wSi7c5DxeRL3RNDEsK2rR6jTWcVclpDQqr1LRU/cPYRvZPuTOfIaz/AOH9JercQnqKLSM3O3hNoaBkqjMuo+haNf8";
    public String generateToken(String userName){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }
    private String createToken( Map<String,Object> claims,String userName){
      return Jwts.builder()
              .setClaims(claims).setSubject(userName)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis()+ 10*60*60*1000))
              .signWith(getSigningKey(), SignatureAlgorithm.HS256)//a9wa wa7dda
              .compact();
    }
    private Key getSigningKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }

    public boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> getSubject) {
        final Claims claims = extractAllClaims(token);
        return getSubject.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
