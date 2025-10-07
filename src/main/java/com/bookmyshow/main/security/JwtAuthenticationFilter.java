package com.bookmyshow.main.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bookmyshow.main.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final CustomUserDetailsService userDetailsService;
	private final TokenService tokenService; // Inject TokenService for Redis validation
	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	// Constructor injection for dependencies
	public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService,
			TokenService tokenService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Skip authentication for public paths
		if (isPublicPath(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		// Extract JWT from request
		String token = extractJwtFromRequest(request);
		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			// Extract username and user details from token
			String username = jwtService.extractUsername(token);
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			// Extract user ID from token and check if token is still valid in Redis
			Long userId = jwtService.extractUserId(token);

			// First check if token exists in Redis
			if (!tokenService.isTokenValid(userId)) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Token has expired or is invalid.");
				return;
			}

			// If token is valid and not expired, proceed with user authentication
			if (jwtService.isTokenValid(token, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);

				// Refresh token TTL in Redis to 10 minutes
				long newTTLInMillis = 10 * 60 * 1000; // 10 minutes
				tokenService.refreshTokenTTL(userId, newTTLInMillis);
			} else {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Invalid token.");
				return;
			}
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Invalid token.");
			return;
		}

		// Proceed with the request chain
		filterChain.doFilter(request, response);
	}

	// Method to check if the requested URI is a public path
	private boolean isPublicPath(String requestUri) {
		List<String> publicPaths = List.of("/bookmyshow/api/city/all", "/bookmyshow/api/events/get-all-events",
				"/bookmyshow/api/city/**", "/venues/city/{city}", "/venues/getAll", "/bookmyshow/venue/getAll",
				"/bookmyshow/api/states", "/bookmyshow/api/events/{id}", "/bookmyshow/api/events/filter",
				"/bookmyshow/auth/**", "/bookmyshow/api/auth/**", "/bookmyshow/swagger-ui/**",
				"/bookmyshow/v3/api-docs/**", "/bookmyshow/api/shows");

		return publicPaths.stream().anyMatch(path -> pathMatcher.match(path, requestUri));
	}

	private String extractJwtFromRequest(HttpServletRequest request) {
		// Extract token from the Authorization header
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7);
		}
		return null; // Return null if no token found
	}
}
