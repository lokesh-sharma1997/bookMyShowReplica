package com.bookmyshow.main.controller;

import org.springframework.http.MediaType; // Import MediaType
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.dto.LoginRequest;
import com.bookmyshow.main.dto.RegisterRequest;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE) // Specify produces
	public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest req) {
		try {
			String token = authService.login(req); // Get JWT token as String
			ApiResponse<String> response = new ApiResponse<>(200, "Login successful", true, token);
			return ResponseEntity.ok(response);
		} catch (RuntimeException ex) {
			ApiResponse<String> errorResponse = new ApiResponse<>(401, ex.getMessage(), false, null);
			return ResponseEntity.status(401).body(errorResponse);
		}
	}

	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE) // Specify produces
	public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterRequest req) {
		try {

			String message = authService.register(req);
			ApiResponse<String> response = new ApiResponse<>(200, "Registration successful", true, message);
			return ResponseEntity.ok(response);
		} catch (RuntimeException ex) {
			ApiResponse<String> errorResponse = new ApiResponse<>(400, ex.getMessage(), false, null);
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}
}
