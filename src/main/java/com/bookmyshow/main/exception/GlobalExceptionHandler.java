//package com.bookmyshow.main.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import com.bookmyshow.main.response.ApiResponse;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//	@ExceptionHandler(UserNotFoundException.class)
//	public ResponseEntity<ApiResponse<Object>> handleUserNotFound(UserNotFoundException ex) {
//		// Create the ApiResponse with relevant details
//		ApiResponse<Object> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), false, null);
//
//		// Return ResponseEntity with proper HTTP Status
//		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//	}
//
//	// Optional: Handle other exceptions generically
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
//		ApiResponse<Object> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
//				"An unexpected error occurred: " + ex.getMessage(), false, null // No data in case of error
//		);
//		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//	}
//}
