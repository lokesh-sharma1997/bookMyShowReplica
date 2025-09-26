package com.bookmyshow.main.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import com.bookmyshow.main.security.JwtService;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtServiceTest {

    private JwtService jwtService;

    // Use a fixed secret key for testing
    private final String secret = "0123456789abcdef0123456789abcdef"; // 32 chars for HS256

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // Manually inject secret and expiration
        jwtService.secret = secret;
        jwtService.expirationMs = 600000; // 10 minutes in ms
    }

    @Test
    void testGenerateTokenAndExtract() {
        String username = "testuser";
        String role = "USER";
        Long userId = 123L;

        String token = jwtService.generateToken(username, role, userId);
        assertNotNull(token);

        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(username, extractedUsername);

        Long extractedUserId = jwtService.extractUserId(token);
        assertEquals(userId, extractedUserId);
    }

    @Test
    void testIsTokenValid_ValidToken() {
        String username = "validuser";
        String role = "ADMIN";
        Long userId = 42L;

        String token = jwtService.generateToken(username, role, userId);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);

        boolean valid = jwtService.isTokenValid(token, userDetails);
        assertTrue(valid);
    }

    @Test
    void testIsTokenValid_InvalidUsername() {
        String username = "user1";
        String role = "USER";
        Long userId = 1L;

        String token = jwtService.generateToken(username, role, userId);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("differentUser");

        boolean valid = jwtService.isTokenValid(token, userDetails);
        assertFalse(valid);
    }

    @Test
    void testGetExpirationMs() {
        assertEquals(600000, jwtService.getExpirationMs());
    }
}
