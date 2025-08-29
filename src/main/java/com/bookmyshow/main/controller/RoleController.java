package com.bookmyshow.main.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import com.bookmyshow.main.dto.RoleDTO;

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

	public ResponseEntity<?> getRoleById(@PathVariable int id) {

		Optional<RoleDTO> role = roleService.getByRoleId(id);

		return role.map(ResponseEntity::ok)

				.orElseGet(() -> ResponseEntity.notFound().build());

	}

	@PreAuthorize("hasRole('ADMIN')")

	@GetMapping("/search/{name}")

	public ResponseEntity<?> getRoleByName(@PathVariable String name) {

		Optional<RoleDTO> role = roleService.getByRoleName(name);

		return role.map(ResponseEntity::ok)

				.orElseGet(() -> ResponseEntity.notFound().build());

	}

	@PreAuthorize("hasRole('ADMIN')")

	@GetMapping("/get-all-roles")

	public ResponseEntity<List<RoleDTO>> getAllRoles() {

		return ResponseEntity.ok(roleService.getAllRoles());

	}

	/**
	 * Create Role
	 * 
	 * @PostMapping("/create-role")
	 * 
	 * public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
	 * 
	 * return ResponseEntity.ok(roleService.createRole(roleDTO));
	 * 
	 * }
	 * 
	 * Delete Role
	 * 
	 * @DeleteMapping("/{id}")
	 * 
	 * public ResponseEntity<?> deleteRole(@PathVariable int id) {
	 * 
	 * boolean deleted = roleService.deleteRole(id);
	 * 
	 * if (deleted) {
	 * 
	 * return ResponseEntity.ok("Role deleted successfully");
	 * 
	 * }
	 * 
	 * return ResponseEntity.notFound().build();
	 * 
	 * }
	 * 
	 **/

}
