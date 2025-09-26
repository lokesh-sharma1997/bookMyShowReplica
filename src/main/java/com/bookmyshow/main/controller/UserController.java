package com.bookmyshow.main.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.dto.UserDTO;
import com.bookmyshow.main.exception.RoleNotFoundException;
import com.bookmyshow.main.exception.UserNotFoundException;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.response.UserResponse;
import com.bookmyshow.main.response.UsersResponse;
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
	public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable int id) {
		UserDTO user = userService.getByUserId(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

		return ResponseEntity.ok(new ApiResponse<>(200, "User found", true, new UserResponse(user)));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "${user.getAllUsers}")
	public ResponseEntity<ApiResponse<UsersResponse>> getAllUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		Page<UserDTO> usersPage = userService.getAllUsers(page, size);

		if (usersPage.isEmpty()) {
			throw new UserNotFoundException("No users found");
		}

		UsersResponse usersResponse = new UsersResponse(usersPage.getContent(), size);
		usersResponse.setTotalEntries((int) usersPage.getTotalElements());

		return ResponseEntity.ok(new ApiResponse<>(200, "Users retrieved", true, usersResponse));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping(value = "/delete-user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "${user.deleteUser}")
	public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable int id) {
		boolean deleted = userService.deleteById(id);
		if (!deleted) {
			throw new UserNotFoundException("User not found with id: " + id);
		}
		return ResponseEntity
				.ok(new ApiResponse<>(200, "User deleted successfully", true, "User with ID " + id + " deleted"));
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<UsersResponse>> globalSearchUser(@RequestParam String value,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		if (value == null || value.trim().isEmpty()) {
			return ResponseEntity.badRequest()
					.body(new ApiResponse<>(400, "Search keyword must be provided", false, null));
		}

		Page<UserDTO> usersPage = userService.searchUser(value.trim(), page, size);

		if (usersPage.isEmpty()) {
			return ResponseEntity.ok(new ApiResponse<>(200, "No user found", true, null));
		}

		UsersResponse usersResponse = new UsersResponse(usersPage.getContent(), size);
		usersResponse.setTotalEntries((int) usersPage.getTotalElements());

		return ResponseEntity.ok(new ApiResponse<>(200, "Users found", true, usersResponse));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/role/{roleName}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "${user.getUserByRole}")
	public ResponseEntity<ApiResponse<UsersResponse>> getByRole(@PathVariable String roleName) {
		List<UserDTO> users;
		try {
			users = userService.getByRole(roleName);
		} catch (IllegalArgumentException e) {
			throw new RoleNotFoundException("Invalid role name: " + roleName);
		}

		return ResponseEntity
				.ok(new ApiResponse<>(200, "Users found with role: " + roleName, true, new UsersResponse(users)));
	}

	@PutMapping("/{userId}/role")
	@Operation(summary = "${user.updateUserRole}")
	public ResponseEntity<ApiResponse<UserDTO>> updateUserRole(@PathVariable int userId) {

		userService.updateUserRole(userId);

		// Fetch updated user and convert to DTO
		UserDTO updatedUser = userService.getByUserId(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		ApiResponse<UserDTO> response = new ApiResponse<>();
		response.setSuccess(true);
		response.setStatusCode(200);
		response.setMessage("User role updated to Admin");
		response.setData(updatedUser);

		return ResponseEntity.ok(response);
	}

}