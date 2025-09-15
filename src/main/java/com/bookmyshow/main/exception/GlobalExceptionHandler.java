package com.bookmyshow.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bookmyshow.main.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	// User not found
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> handleUserNotFound(UserNotFoundException ex) {
		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), false);
	}

	// Role not found
	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> handleRoleNotFound(RoleNotFoundException ex) {
		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), false);
	}

	// State not found
	@ExceptionHandler(StateNotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> handleStateNotFound(StateNotFoundException ex) {
		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), false);
	}

	// Invalid credentials
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ApiResponse<Object>> handleInvalidCredentials(InvalidCredentialsException ex) {
		return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), false);
	}

	// Resource already exists
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<Object>> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
		return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), false);
	}

	// Catch-all for any other exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error: " + ex.getMessage(), false);
	}

	// Event custom exception
	@ExceptionHandler(EventCustomException.class)
	public ResponseEntity<ApiResponse<Object>> handleEventCustomException(EventCustomException ex) {
		return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), false);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
		// Format the error message
		String formattedErrors = ex.getFormattedErrors();

		// Return a 400 Bad Request with the formatted error message
		return new ResponseEntity<>(formattedErrors, HttpStatus.BAD_REQUEST);
	}

	// 🔹 Common builder method
	private ResponseEntity<ApiResponse<Object>> buildResponse(HttpStatus status, String message, boolean success) {
		ApiResponse<Object> response = new ApiResponse<>(status.value(), message, success, null);
		return new ResponseEntity<>(response, status);
	}
}
