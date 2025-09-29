package com.bookmyshow.main.Security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.bookmyshow.main.security.CustomUserDetailsService;
import com.bookmyshow.main.security.JwtAuthenticationFilter;
import com.bookmyshow.main.security.JwtService;
import com.bookmyshow.main.service.TokenService;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        // Prepare a writer to capture response output
        responseWriter = new StringWriter();
        lenient().when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));


        // Clear security context before each test
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_PublicPath_ShouldProceedWithoutAuthentication() throws Exception {
        when(request.getRequestURI()).thenReturn("/bookmyshow/api/city/all");

        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_NoAuthorizationHeader_ShouldProceedWithoutAuthentication() throws Exception {
        when(request.getRequestURI()).thenReturn("/private/path");
        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_TokenNotInRedis_ShouldReturn401() throws Exception {
        String token = "mockToken";
        String username = "user";
        Long userId = 1L;

        when(request.getRequestURI()).thenReturn("/private/path");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(mock(UserDetails.class));
        when(jwtService.extractUserId(token)).thenReturn(userId);

        when(tokenService.isTokenValid(userId)).thenReturn(false);

        filter.doFilter(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        assertTrue(responseWriter.toString().contains("Token has expired or is invalid."));
        verify(filterChain, never()).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_TokenInvalidAccordingToJwtService_ShouldReturn401() throws Exception {
        String token = "mockToken";
        String username = "user";
        Long userId = 1L;

        UserDetails userDetails = mock(UserDetails.class);

        when(request.getRequestURI()).thenReturn("/private/path");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.extractUserId(token)).thenReturn(userId);

        when(tokenService.isTokenValid(userId)).thenReturn(true);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(false);

        filter.doFilter(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        assertTrue(responseWriter.toString().contains("Invalid token."));
        verify(filterChain, never()).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_ValidToken_ShouldSetAuthenticationAndRefreshTTL() throws Exception {
        String token = "mockToken";
        String username = "user";
        Long userId = 1L;

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getAuthorities()).thenReturn(List.of());

        when(request.getRequestURI()).thenReturn("/private/path");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.extractUserId(token)).thenReturn(userId);

        when(tokenService.isTokenValid(userId)).thenReturn(true);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);

        filter.doFilter(request, response, filterChain);

        verify(tokenService).refreshTokenTTL(eq(userId), eq(600000L)); // 10 minutes in ms
        verify(filterChain).doFilter(request, response);

        // Check that authentication was set in SecurityContext
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertTrue(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken);
        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Test
    void testDoFilterInternal_ExceptionInTokenProcessing_ShouldReturn401() throws Exception {
        String token = "mockToken";

        when(request.getRequestURI()).thenReturn("/private/path");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        when(jwtService.extractUsername(token)).thenThrow(new RuntimeException("some error"));

        filter.doFilter(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        assertTrue(responseWriter.toString().contains("Invalid token."));
        verify(filterChain, never()).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
