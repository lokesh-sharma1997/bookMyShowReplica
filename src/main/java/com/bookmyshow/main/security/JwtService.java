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
	public String secret;

	@Value("${app.jwt.expiration-ms}")
	public long expirationMs;

	// Returns the secret key used to sign JWT tokens
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	// Returns the token expiration time in milliseconds
	public long getExpirationMs() {
		return expirationMs;
	}

	// Generates a JWT token with username, role, and userId as claims
	public String generateToken(String username, String role, Long userId) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);
		claims.put("userId", userId);

		return createToken(claims, username);
	}

	// Builds and signs the JWT token with provided claims and subject
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().claims(claims).subject(subject).header().empty().add("typ", "JWT").and()
				// .issuedAt(new Date(System.currentTimeMillis()))
				// .expiration(new Date(System.currentTimeMillis() + expirationMs))
				.signWith(getSigningKey()).compact();
	}

	// Extracts all claims from the JWT token
	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}

	// Extracts the username (subject) from the token
	public String extractUsername(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getSubject();
	}

	// Extracts the userId claim from the token
	public Long extractUserId(String token) {
		return extractAllClaims(token).get("userId", Long.class);
	}

	// Extracts the expiration date of the token
	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}

	// Validates if the token's username matches the given user's username
	public Boolean isTokenValid(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		Long userId = extractUserId(token);

		return username.equals(userDetails.getUsername());
	}
}
