package com.bookmyshow.main.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.dto.LoginRequest;
import com.bookmyshow.main.dto.RegisterRequest;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.response.TokenResponse;
import com.bookmyshow.main.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginRequest req) {
	    String token = authService.login(req);
	    return ResponseEntity.ok(new ApiResponse<>(200, "Login successful", true, new TokenResponse(token)));
	}

	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE) // Specify produces
	public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterRequest req) {
		String message = authService.register(req);
	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body(new ApiResponse<>(201, message, true, null));
	}
}
