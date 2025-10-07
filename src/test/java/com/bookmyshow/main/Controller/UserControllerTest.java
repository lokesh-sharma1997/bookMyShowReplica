package com.bookmyshow.main.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bookmyshow.main.controller.UserController;
import com.bookmyshow.main.dto.UserDTO;
import com.bookmyshow.main.exception.RoleNotFoundException;
import com.bookmyshow.main.exception.UserNotFoundException;
import com.bookmyshow.main.response.*;
import com.bookmyshow.main.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	private UserDTO user;

	@BeforeEach
	void setup() {
		user = new UserDTO();
		user.setUserId(1L);
		user.setUsername("kashish11");
		user.setEmail("kashish@example.com");

	}

	// getUserById success
	@Test
	void testGetUserById_Found() {
		when(userService.getByUserId(1)).thenReturn(Optional.of(user));

		ResponseEntity<ApiResponse<UserResponse>> response = userController.getUserById(1);

		assertEquals(200, response.getBody().getStatusCode());
		assertEquals("User found", response.getBody().getMessage());
		assertEquals(user.getUsername(), response.getBody().getData().getUser().getUsername());
	}

	// getUserById not found
	@Test
	void testGetUserById_NotFound() {
		when(userService.getByUserId(2)).thenReturn(Optional.empty());

		UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
			userController.getUserById(2);
		});

		assertEquals("User not found with id: 2", thrown.getMessage());
	}

	// getAllUsers success
	@Test
	void testGetAllUsers_Success() {
		Page<UserDTO> page = new PageImpl<>(List.of(user));
		when(userService.getAllUsers(0, 10)).thenReturn(page);

		ResponseEntity<ApiResponse<UsersResponse>> response = userController.getAllUsers(0, 10);

		assertEquals(200, response.getBody().getStatusCode());
		assertEquals("Users fetched successfully", response.getBody().getMessage());
		assertEquals(1, response.getBody().getData().getUsers().size());
	}

	// getAllUsers no users found -> throws exception
	@Test
	void testGetAllUsers_NoUsers() {
		Page<UserDTO> emptyPage = Page.empty();
		when(userService.getAllUsers(0, 10)).thenReturn(emptyPage);

		ResponseEntity<ApiResponse<UsersResponse>> response = userController.getAllUsers(0, 10);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().isSuccess());
		assertEquals("No users found", response.getBody().getMessage());
		assertTrue(response.getBody().getData().getUsers().isEmpty());
		assertEquals(0, response.getBody().getData().getTotalEntries());
	}

	// deleteUser success
	@Test
	void testDeleteUser_Success() {
		when(userService.deleteById(1)).thenReturn(true);

		ResponseEntity<ApiResponse<String>> response = userController.deleteUser(1);

		assertEquals(200, response.getBody().getStatusCode());
		assertEquals("User deleted successfully", response.getBody().getMessage());
		assertTrue(response.getBody().isSuccess());
	}

	// deleteUser failure (user not found)
	@Test
	void testDeleteUser_NotFound() {
		when(userService.deleteById(2)).thenReturn(false);

		UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
			userController.deleteUser(2);
		});

		assertEquals("User not found with id: 2", thrown.getMessage());
	}

	// globalSearchUser success with results
	@Test
	void testGlobalSearchUser_WithResults() {
		Page<UserDTO> page = new PageImpl<>(List.of(user));
		when(userService.searchUser("kashish", 0, 10)).thenReturn(page);

		ResponseEntity<ApiResponse<UsersResponse>> response = userController.globalSearchUser("kashish", 0, 10);

		assertEquals(200, response.getBody().getStatusCode());
		assertEquals("Users fetched successfully", response.getBody().getMessage());
		assertNotNull(response.getBody().getData());
		assertEquals(1, response.getBody().getData().getUsers().size());
	}

	// globalSearchUser no results
	@Test
	void testGlobalSearchUser_NoResults() {
		Page<UserDTO> emptyPage = Page.empty();
		when(userService.searchUser("emptylist", 0, 10)).thenReturn(emptyPage);

		ResponseEntity<ApiResponse<UsersResponse>> response = userController.globalSearchUser("emptylist", 0, 10);

		assertEquals(200, response.getBody().getStatusCode());
		assertEquals("No users found", response.getBody().getMessage());
		assertTrue(response.getBody().getData().getUsers().isEmpty());
		assertEquals(0, response.getBody().getData().getTotalEntries());
	}

	// globalSearchUser bad request (empty search value)
	@Test
	void testGlobalSearchUser_EmptyValue() {
		ResponseEntity<ApiResponse<UsersResponse>> response = userController.globalSearchUser(" ", 0, 10);

		assertEquals(400, response.getBody().getStatusCode());
		assertEquals("Search keyword must be provided", response.getBody().getMessage());
		assertFalse(response.getBody().isSuccess());
		assertNull(response.getBody().getData());
	}

	// getByRole success
	@Test
	void testGetByRole_Success() {
		List<UserDTO> users = List.of(user);
		when(userService.getByRole("USER")).thenReturn(users);

		ResponseEntity<ApiResponse<UsersResponse>> response = userController.getByRole("USER");

		assertEquals(200, response.getBody().getStatusCode());
		assertEquals("Users found with role: USER", response.getBody().getMessage());
		assertEquals(1, response.getBody().getData().getUsers().size());
	}

	// getByRole invalid role -> RoleNotFoundException
	@Test
	void testGetByRole_InvalidRole() {
		when(userService.getByRole("INVALID")).thenThrow(new IllegalArgumentException("Invalid role"));

		RoleNotFoundException thrown = assertThrows(RoleNotFoundException.class, () -> {
			userController.getByRole("INVALID");
		});

		assertEquals("Invalid role name: INVALID", thrown.getMessage());
	}

	// updateUserRole success
	@Test
	void testUpdateUserRole_Success() {
		// Mocking userService.updateUserRole (void method)
		doNothing().when(userService).updateUserRole(1);

		// Mocking userService.getByUserId to return user wrapped in Optional
		when(userService.getByUserId(1)).thenReturn(Optional.of(user));

		// Call the controller method
		ResponseEntity<ApiResponse<UserDTO>> response = userController.updateUserRole(1);

		// Assertions
		assertNotNull(response);
		assertEquals(200, response.getBody().getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().isSuccess());
		assertEquals("User role updated to Admin", response.getBody().getMessage());
		assertEquals(user, response.getBody().getData());

		// Verify mocks called
		verify(userService).updateUserRole(1);
		verify(userService).getByUserId(1);
	}

	@Test
	void testUpdateUserRole_UserNotFound() {
		doNothing().when(userService).updateUserRole(2);

		// Return empty Optional to simulate user not found
		when(userService.getByUserId(2)).thenReturn(Optional.empty());

		// Calling the method should throw exception
		UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
			userController.updateUserRole(2);
		});

		assertEquals("User not found with ID: 2", exception.getMessage());
	}

}
