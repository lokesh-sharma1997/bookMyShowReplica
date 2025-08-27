package com.bookmyshow.main.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.dto.UserDTO;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "${user.getUserById}")
	public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable int id) {
		Optional<UserDTO> user = userService.getByUserId(id);
		if (user.isPresent()) {
			ApiResponse<UserDTO> response = new ApiResponse<>(200, "User found", true, user.get());
			return ResponseEntity.ok(response);
		} else {
			ApiResponse<UserDTO> errorResponse = new ApiResponse<>(404, "User not found", false, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<Object>> getAllUsers() {
		List<UserDTO> users = userService.getAllUsers();

		if (users.isEmpty()) {
			ApiResponse<Object> errorResponse = new ApiResponse<>(404, "No users found", false, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
		ApiResponse<Object> response = new ApiResponse<>(200, "All users retrieved", true, users);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping(value = "/delete-user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable int id) {
		boolean deleted = userService.deleteById(id);
		if (deleted) {
			ApiResponse<String> response = new ApiResponse<>(200, "User deleted successfully", true,
					"User with ID " + id + " deleted");
			return ResponseEntity.ok(response);
		} else {
			ApiResponse<String> errorResponse = new ApiResponse<>(404, "User not found", false, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@GetMapping(value = "/search/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<Object>> getByName(@PathVariable String name) {
		List<UserDTO> users = userService.getByName(name);

		if (users.isEmpty()) {

			ApiResponse<Object> errorResponse = new ApiResponse<>(404, "No users found with name: " + name, false,
					null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
		ApiResponse<Object> response = new ApiResponse<>(200, "Users found", true, users);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@GetMapping(value = "/search/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<UserDTO>> getByUsername(@PathVariable String username) {
		Optional<UserDTO> user = userService.getByUsername(username);
		if (user.isPresent()) {
			ApiResponse<UserDTO> response = new ApiResponse<>(200, "User found", true, user.get());
			return ResponseEntity.ok(response);
		} else {
			ApiResponse<UserDTO> errorResponse = new ApiResponse<>(404, "User not found", false, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/role/{roleName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<Object>> getByRole(@PathVariable String roleName) {
		try {
			List<UserDTO> users = userService.getByRole(roleName);

			if (users.isEmpty()) {
				ApiResponse<Object> errorResponse = new ApiResponse<>(404, "No users found with role: " + roleName,
						false, null);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
			}

			ApiResponse<Object> response = new ApiResponse<>(200, "Users found with role: " + roleName, true, users);
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			ApiResponse<Object> errorResponse = new ApiResponse<>(400, "Invalid role name: " + roleName, false, null);
			return ResponseEntity.badRequest().body(errorResponse);
		} catch (Exception e) {
			ApiResponse<Object> errorResponse = new ApiResponse<>(500, "An unexpected error occurred", false, null);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@GetMapping(value = "/search/phone/{phone}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<UserDTO>> getByPhone(@PathVariable String phone) {
		Optional<UserDTO> user = userService.getByPhoneNumber(phone);
		if (user.isPresent()) {
			ApiResponse<UserDTO> response = new ApiResponse<>(200, "User found", true, user.get());
			return ResponseEntity.ok(response);
		} else {
			ApiResponse<UserDTO> errorResponse = new ApiResponse<>(404, "User not found", false, null);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}
}
