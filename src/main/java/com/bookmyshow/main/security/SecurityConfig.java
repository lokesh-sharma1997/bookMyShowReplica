package com.bookmyshow.main.security;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(CustomUserDetailsService userDetailsService,
			JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authz -> authz
				// Permitting all GET requests and filtering movies
				.requestMatchers("/events/get-all-events", "/city/**").permitAll() // Allow GET requests to movies and
																					// filters
				.requestMatchers("/events/filter").permitAll() // Allow filtering movies for all users

				// Restricting the movie creation (POST), update (PUT), and delete (PATCH)
				// operations
				.requestMatchers(HttpMethod.POST, "/events/create-event").authenticated() // Only authenticated users
																							// can create a movie
				.requestMatchers(HttpMethod.PUT, "/events/update/**").hasRole("ADMIN") // Only ADMIN role can update
																						// movies
				.requestMatchers(HttpMethod.PATCH, "/events/delete/**").hasRole("ADMIN") // Only ADMIN role can delete
																							// movies
				.requestMatchers(HttpMethod.POST, "/venue/create-venue/**").hasRole("ADMIN") // Only ADMIN role can
																								// delete movies

				// Allowing all authentication related endpoints
				.requestMatchers("/auth/**", "/api/auth/**").permitAll() // Public auth endpoints (e.g., login,
																			// registration)

				// Swagger UI and API documentation
				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

				// Any other request requires authentication
				.anyRequest().authenticated()).cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enabling
																													// CORS
				.userDetailsService(userDetailsService) // Custom user details service
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Adding JWT
																										// filter before
																										// authentication
																										// filter

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(List.of("*"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
}