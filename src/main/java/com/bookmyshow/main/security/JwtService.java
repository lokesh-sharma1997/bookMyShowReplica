package com.bookmyshow.main.security;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Claims;

@Component
public class JwtService {

	@Value("${app.jwt.secret}")
	String secret;
	@Value("${app.jwt.expiration-ms}")
	long expirationMs;

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	public long getExpirationMs() {
		return expirationMs;
	}

	public String generateToken(String username, String role, Long userId) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);
		claims.put("userId", userId);

		return createToken(claims, username);
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().claims(claims).subject(subject).header().empty().add("typ", "JWT").and()
				//.issuedAt(new Date(System.currentTimeMillis()))
				//.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // 10 minutes expiration time
				.signWith(getSigningKey()).compact();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}

	public String extractUsername(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getSubject();
	}

	public Long extractUserId(String token) {
		return extractAllClaims(token).get("userId", Long.class);
	}

	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}

//	public Boolean validateToken(String token) {
//		return !isTokenExpired(token);
//	}
//
//	public Boolean isTokenExpired(String token) {
//		return extractExpiration(token).before(new Date());
//	}
	 public Boolean isTokenValid(String token, UserDetails userDetails) {
	        String username = extractUsername(token);
	        Long userId = extractUserId(token);

	        // Compare token username with userDetails username and user ID
	        return username.equals(userDetails.getUsername());
	    }
}