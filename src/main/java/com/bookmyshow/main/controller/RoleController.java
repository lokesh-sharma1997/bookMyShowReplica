package com.bookmyshow.main.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.bookmyshow.main.dto.RoleDTO;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.service.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

	private final RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<RoleDTO>> getRoleById(@PathVariable int id) {
		Optional<RoleDTO> role = roleService.getByRoleId(id);

		if (role.isPresent()) {
			return ResponseEntity.ok(
				new ApiResponse<>(200, "Role found", true, role.get())
			);
		} else {
			// Keep null here since RoleDTO is a single object
			return ResponseEntity.status(404).body(
				new ApiResponse<>(404, "Role not found", false, null)
			);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/search/{name}")
	public ResponseEntity<ApiResponse<RoleDTO>> getRoleByName(@PathVariable String name) {
		Optional<RoleDTO> role = roleService.getByRoleName(name);

		if (role.isPresent()) {
			return ResponseEntity.ok(
				new ApiResponse<>(200, "Role found", true, role.get())
			);
		} else {
			// Keep null here since RoleDTO is a single object
			return ResponseEntity.status(404).body(
				new ApiResponse<>(404, "Role not found", false, null)
			);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get-all-roles")
	public ResponseEntity<ApiResponse<List<RoleDTO>>> getAllRoles() {
		List<RoleDTO> roles = roleService.getAllRoles();

		if (roles.isEmpty()) {
			return ResponseEntity.status(404).body(
				// Return empty list instead of null
				new ApiResponse<>(404, "No roles found", false, Collections.emptyList())
			);
		}

		return ResponseEntity.ok(
			new ApiResponse<>(200, "All roles retrieved successfully", true, roles)
		);
	}

	/*
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create-role")
	public ResponseEntity<ApiResponse<RoleDTO>> createRole(@RequestBody RoleDTO roleDTO) {
		RoleDTO createdRole = roleService.createRole(roleDTO);
		return ResponseEntity.ok(
			new ApiResponse<>(201, "Role created successfully", true, createdRole)
		);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteRole(@PathVariable int id) {
		boolean deleted = roleService.deleteRole(id);
		if (deleted) {
			return ResponseEntity.ok(
				new ApiResponse<>(200, "Role deleted successfully", true, "Role with ID " + id + " deleted")
			);
		}
		return ResponseEntity.status(404).body(
			new ApiResponse<>(404, "Role not found with id: " + id, false, "")
		);
	}
	*/

}
