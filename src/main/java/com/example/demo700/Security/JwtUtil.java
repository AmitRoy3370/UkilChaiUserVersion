package com.example.demo700.Security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import io.jsonwebtoken.ExpiredJwtException;


@Component
public class JwtUtil {

	private static final String SECRET_KEY = "this_is_my_super_secret_key_for_jwt_token_123456"; 

	private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 20000 * 60 * 60)) 
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	public String extractUsername(String token) {
	    try {
	        return Jwts.parserBuilder()
	                .setSigningKey(key)
	                .build()
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject();
	    } catch (ExpiredJwtException e) {
	        System.out.println("Token expired: " + e.getMessage());
	        return null;
	    } catch (Exception e) {
	        System.out.println("Invalid token: " + e.getMessage());
	        return null;
	    }
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

}

