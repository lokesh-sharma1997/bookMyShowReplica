package com.bookmyshow.main.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.bookmyshow.main.controller.RoleController;
import com.bookmyshow.main.dto.RoleDTO;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.response.StateResponse;
import com.bookmyshow.main.service.RoleService;

@ExtendWith(MockitoExtension.class)
public class RolecontrollerTest {
	@Mock
	private RoleService roleService;
	@InjectMocks
	private RoleController roleController;
	private RoleDTO role;

	@BeforeEach
	void setUp() {
		role = new RoleDTO();
		role.setRoleId(1);
		role.setRoleName("USER");
	}

	@Test
	void getRoleById_Found() {
		when(roleService.getByRoleId(1)).thenReturn(Optional.of(role));
		ResponseEntity<ApiResponse<RoleDTO>> response = roleController.getRoleById(1);
		assertEquals(200, response.getBody().getStatusCode());
		assertEquals("Role found", response.getBody().getMessage());
	}

	@Test
	void getRoleById_NotFound() {
		when(roleService.getByRoleId(3)).thenReturn(Optional.empty());
		ResponseEntity<ApiResponse<RoleDTO>> response = roleController.getRoleById(3);
		assertEquals(404, response.getBody().getStatusCode());
		assertEquals("Role not found", response.getBody().getMessage());
	}

	@Test
	void getRoleByName_Found() {
		when(roleService.getByRoleName("USER")).thenReturn(Optional.of(role));
		ResponseEntity<ApiResponse<RoleDTO>> response = roleController.getRoleByName("USER");
		assertEquals(200, response.getBody().getStatusCode());
		assertEquals("Role found", response.getBody().getMessage());
	}

	@Test
	void getRoleByName_NotFound() {
		when(roleService.getByRoleName("USERS")).thenReturn(Optional.empty());
		ResponseEntity<ApiResponse<RoleDTO>> response = roleController.getRoleByName("USERS");
		assertEquals(404, response.getBody().getStatusCode());
		assertEquals("Role not found", response.getBody().getMessage());
	}

	@Test
	void getAllRoles() {
		RoleDTO role2 = new RoleDTO();
		role2.setRoleId(2);
		role2.setRoleName("ADMIN");

		List<RoleDTO> roles = Arrays.asList(role, role2);

		when(roleService.getAllRoles()).thenReturn(roles);

		ResponseEntity<ApiResponse<List<RoleDTO>>> response = roleController.getAllRoles();

		assertEquals(200, response.getBody().getStatusCode());

		ApiResponse<List<RoleDTO>> responseBody = response.getBody();
		assert responseBody != null;
		assertEquals(200, responseBody.getStatusCode());
		assertEquals("All roles retrieved successfully", responseBody.getMessage());
		assertEquals(2, responseBody.getData().size());
	}

	@Test
	void testGetAllRoles_WhenNoRolesFound() {

		when(roleService.getAllRoles()).thenReturn(Arrays.asList());
		ResponseEntity<ApiResponse<List<RoleDTO>>> result = roleController.getAllRoles();
		assertEquals(404, result.getBody().getStatusCode());
		ApiResponse<List<RoleDTO>> response = result.getBody();
		assert response != null;
		assertEquals("No roles found", response.getMessage());
		assertEquals(0, response.getData().size());
	}

}
