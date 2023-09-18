package com.gametags.gametags.infrastructure;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JWTGenerator {

  public String generateToken(Authentication authentication){
    String username = authentication.getName();
    Date currentDate = new Date();
    Date expiredDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
    String token = Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(expiredDate)
        .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
        .compact();
    return token;
  }

  public String getUsernameFromJWT(String token){
    Claims claims = Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET)
        .parseClaimsJws(token)
        .getBody();
    return claims.getSubject();
  }

  public boolean validateToken(String token){
    try {
      Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token);
      return true;
    }catch(Exception e){
      throw new AuthenticationCredentialsNotFoundException("JWT was expire or incorrect");
    }
  }

}
