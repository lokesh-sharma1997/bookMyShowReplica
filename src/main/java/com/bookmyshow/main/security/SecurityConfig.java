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

import com.bookmyshow.main.service.TokenService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;
	private final JwtService jwtService;
	private final TokenService tokenService;

	// Constructor injection for dependencies
	public SecurityConfig(CustomUserDetailsService userDetailsService, JwtService jwtService,
			TokenService tokenService) {
		this.userDetailsService = userDetailsService;
		this.jwtService = jwtService;
		this.tokenService = tokenService;
	}

	// Register JwtAuthenticationFilter as a Spring Bean
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtService, userDetailsService, tokenService);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authz -> authz
				// Permitting all GET requests and filtering movies
				.requestMatchers("/api/events/get-all-events", "/venues/city/{city}", "/venues/getAll", "/api/city/**",
						"/venue/getAll", "/api/states", "/api/events/{id}", "/api/events/filter", "/auth/**",
						"/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**")
				.permitAll().anyRequest().authenticated())
				.cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enabling CORS
				.userDetailsService(userDetailsService) // Custom user details service
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Add JWT
																											// filter
																											// before

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(List.of("*"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
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
	// public static void main(String[] args) throws Exception {
//		String key = "U29tZVNlY3JldEtleVRoYXRJc1ZlcnlTZWN1cmUhISE=";
//   String password = "Kashish@2004";
//	  //  fGM/pEkdMgb8KK7POUA6HA==
//	    String encrypted = AESUtil.encrypt(password, key);
//	    String decrypted = AESUtil.decrypt(encrypted, key);
//
//	    System.out.println("Encrypted: " + encrypted);
//	    System.out.println("Decrypted: " + decrypted);
//
//	}
}
