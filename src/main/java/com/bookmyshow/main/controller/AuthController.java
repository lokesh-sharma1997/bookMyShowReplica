package com.bookmyshow.main.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.dto.LoginRequest;
import com.bookmyshow.main.dto.RegisterRequest;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.response.TokenResponse;
import com.bookmyshow.main.service.AuthService;
import com.bookmyshow.main.service.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	@Value("${app.jwt.secret}")
	String secretKey;
	private final TokenService tokenService;
	private final AuthService authService;
	

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "${auth.login}")
	public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginRequest req) {
		String token = authService.login(req);
		return ResponseEntity.ok(new ApiResponse<>(200, "Login successful", true, new TokenResponse(token)));
	}

	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE) // Specify produces
	@Operation(summary = "${auth.register}")
	public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest req, BindingResult bindingResult) {

	    // Check if there are validation errors
	    if (bindingResult.hasErrors()) {
	        // StringBuilder for storing the error messages
	        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
	        
	        // Iterate over field errors and append them to the error message
	        bindingResult.getFieldErrors().forEach(error -> {
	            errorMessage.append(error.getField())
	                        .append(" - ")
	                        .append(error.getDefaultMessage())
	                        .append("; ");
	        });
	        
	        // Return a custom error response with the validation messages
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                             .body(new ApiResponse<>(400, errorMessage.toString(), false, null));
	    }

	    // Proceed with registration if no validation errors
	    String message = authService.register(req);

	    // Return success response
	    return ResponseEntity.status(HttpStatus.CREATED)
	                         .body(new ApiResponse<>(201, message, true, null));
	}


	@GetMapping("/validate/username")
	@Operation(summary = "${user.validateUsername}")
	public ResponseEntity<ApiResponse<Boolean>> validateUsername(@RequestParam String username) {
		boolean exists = authService.userExistsByUsername(username);

		ApiResponse<Boolean> response = new ApiResponse<>();
		response.setStatusCode(200);
		response.setSuccess(exists ? true:false);
		response.setMessage(exists ? "Username already exists" : "Username available");
		response.setData(exists);

		return ResponseEntity.ok(response);
	}
	@GetMapping("/validate/token")
	@Operation(summary = "Validate token by userId only")
	public ResponseEntity<ApiResponse<Boolean>> validateToken(@RequestParam Long userId) {
	    boolean valid = tokenService.isTokenValid(userId);

	    ApiResponse<Boolean> response = new ApiResponse<>();
	    response.setStatusCode(valid ? 200 : 401);
	    response.setSuccess(valid);
	    response.setMessage(valid ? "Token is valid for user" : "No valid tokens found for user");
	    response.setData(valid);

	    return ResponseEntity.status(valid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED).body(response);
	}
	@PostMapping("/logout")
	@Operation(summary = "Logout user by deleting all tokens using userId")
	public ResponseEntity<ApiResponse<Void>> logout(@RequestParam Long userId) {
	    tokenService.deleteTokenFromRedis(userId);

	    ApiResponse<Void> response = new ApiResponse<>();
	    response.setStatusCode(200);
	    response.setSuccess(true);
	    response.setMessage("Logout successful, all tokens removed for userId = " + userId);
	    response.setData(null);

	    return ResponseEntity.ok(response);
	}
}
