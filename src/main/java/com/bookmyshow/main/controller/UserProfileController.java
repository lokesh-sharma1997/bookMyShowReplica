package com.bookmyshow.main.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.bookmyshow.main.dto.EditProfileRequest;
import com.bookmyshow.main.exception.InvalidCredentialsException;
import com.bookmyshow.main.exception.ResourceAlreadyExistsException;
import com.bookmyshow.main.exception.UserNotFoundException;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.response.UserProfileResponse;
import com.bookmyshow.main.service.UserProfileService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

	private final UserProfileService userProfileService;

	public UserProfileController(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping(value = "/{id}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "${user.getProfileById}")
	public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(@PathVariable Long id) {
		try {
			UserProfileResponse profile = userProfileService.getProfile(id);
			return ResponseEntity.ok(new ApiResponse<>(200, "Profile found", true, profile));
		} catch (Exception e) {
			throw new UserNotFoundException("User not found with id: " + id);
		}
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping(value = "/{id}/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "${user.postProfileById}")
	public ResponseEntity<ApiResponse<UserProfileResponse>> updateProfile(@PathVariable Long id,
			@Valid @RequestBody EditProfileRequest request, BindingResult bindingResult) {

		// Check if there are validation errors
		if (bindingResult.hasErrors()) {
			// for storing the error messages
			StringBuilder errorMessage = new StringBuilder("Validation failed: ");

			// Iterate over field errors and append them to the error message
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
			}

			throw new InvalidCredentialsException(errorMessage.toString());
		}

		try {
			// Proceed with profile update
			UserProfileResponse updatedProfile = userProfileService.updateProfile(id, request);
			return ResponseEntity.ok(new ApiResponse<>(200, "Profile updated successfully", true, updatedProfile));
		} catch (ResourceAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(409, e.getMessage(), false, null));
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>(404, e.getMessage(), false, null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(500, "An unexpected error occurred", false, null));
		}
	}
}
