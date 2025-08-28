package com.bookmyshow.main.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.dto.UserDTO;
import com.bookmyshow.main.exception.UserNotFoundException;
import com.bookmyshow.main.exception.RoleNotFoundException;
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
        UserDTO user = userService.getByUserId(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return ResponseEntity.ok(new ApiResponse<>(200, "User found", true, user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found");
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "All users retrieved", true, users));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/delete-user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteById(id);
        if (!deleted) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "User deleted successfully", true,
                "User with ID " + id + " deleted"));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(value = "/search/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<UserDTO>>> getByName(@PathVariable String name) {
        List<UserDTO> users = userService.getByName(name);
        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found with name: " + name);
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Users found", true, users));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(value = "/search/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserDTO>> getByUsername(@PathVariable String username) {
        UserDTO user = userService.getByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return ResponseEntity.ok(new ApiResponse<>(200, "User found", true, user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/role/{roleName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<UserDTO>>> getByRole(@PathVariable String roleName) {
        List<UserDTO> users;
        try {
            users = userService.getByRole(roleName);
        } catch (IllegalArgumentException e) {
            throw new RoleNotFoundException("Invalid role name: " + roleName);
        }

        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found with role: " + roleName);
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Users found with role: " + roleName, true, users));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(value = "/search/phone/{phone}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserDTO>> getByPhone(@PathVariable String phone) {
        UserDTO user = userService.getByPhoneNumber(phone)
                .orElseThrow(() -> new UserNotFoundException("User not found with phone: " + phone));
        return ResponseEntity.ok(new ApiResponse<>(200, "User found", true, user));
    }
}
