package com.bookmyshow.main.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;

import com.bookmyshow.main.controller.UserProfileController;
import com.bookmyshow.main.dto.EditProfileRequest;
import com.bookmyshow.main.exception.*;
import com.bookmyshow.main.response.*;
import com.bookmyshow.main.service.UserProfileService;

@ExtendWith(MockitoExtension.class)
public class UserProfileControllerTest {

	@Mock
	private UserProfileService userProfileService;

	@InjectMocks
	private UserProfileController userProfileController;

	private UserProfileResponse sampleProfile;

	@BeforeEach
	void setup() {
		sampleProfile = new UserProfileResponse();
		sampleProfile.setId(1L);
		sampleProfile.setName("John Doe");
		sampleProfile.setEmail("john.doe@example.com");
		sampleProfile.setPhoneNumber("1234567890");
		// set other fields if needed
	}

	// Test getProfile success
	@Test
	void testGetProfile_Success() {
		when(userProfileService.getProfile(1L)).thenReturn(sampleProfile);

		ResponseEntity<ApiResponse<UserProfileResponse>> response = userProfileController.getProfile(1L);

		assertEquals(200, response.getBody().getStatusCode());
		assertEquals("Profile found", response.getBody().getMessage());
		assertEquals(sampleProfile, response.getBody().getData());
	}

	// Test getProfile failure - user not found
	@Test
	void testGetProfile_UserNotFound() {
		when(userProfileService.getProfile(2L)).thenThrow(new UserNotFoundException("User not found with id: 2"));

		try {
			userProfileController.getProfile(2L);
		} catch (UserNotFoundException ex) {
			assertEquals("User not found with id: 2", ex.getMessage());
		}
	}

	// Test updateProfile success
	@Test
	void testUpdateProfile_Success() {
		EditProfileRequest request = createValidEditProfileRequest();

		when(userProfileService.updateProfile(eq(1L), any(EditProfileRequest.class))).thenReturn(sampleProfile);

		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		ResponseEntity<ApiResponse<UserProfileResponse>> response = userProfileController.updateProfile(1L, request,
				bindingResult);

		assertEquals(200, response.getBody().getStatusCode());
		assertEquals("Profile updated successfully", response.getBody().getMessage());
		assertEquals(sampleProfile, response.getBody().getData());
	}

	// Test updateProfile validation errors
	@Test
	void testUpdateProfile_ValidationErrors() {
		EditProfileRequest request = createValidEditProfileRequest();

		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);

		FieldError fieldError = new FieldError("editProfileRequest", "email", "Email should be a valid email address");
		when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError));

		try {
			userProfileController.updateProfile(1L, request, bindingResult);
		} catch (InvalidCredentialsException ex) {
			// Validate error message contains field error info
			String message = ex.getMessage();
			assert (message.contains("email - Email should be a valid email address"));
		}
	}

	// Test updateProfile user not found
	@Test
	void testUpdateProfile_UserNotFound() {
		EditProfileRequest request = createValidEditProfileRequest();

		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		when(userProfileService.updateProfile(eq(2L), any(EditProfileRequest.class)))
				.thenThrow(new UserNotFoundException("User not found with id: 2"));

		ResponseEntity<ApiResponse<UserProfileResponse>> response = userProfileController.updateProfile(2L, request,
				bindingResult);

		assertEquals(404, response.getBody().getStatusCode());
		assertEquals("User not found with id: 2", response.getBody().getMessage());
		assertEquals(false, response.getBody().isSuccess());
		assertEquals(null, response.getBody().getData());
	}

	// Test updateProfile resource already exists (conflict)
	@Test
	void testUpdateProfile_ResourceAlreadyExists() {
		EditProfileRequest request = createValidEditProfileRequest();

		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		when(userProfileService.updateProfile(eq(3L), any(EditProfileRequest.class)))
				.thenThrow(new ResourceAlreadyExistsException("Email already exists"));

		ResponseEntity<ApiResponse<UserProfileResponse>> response = userProfileController.updateProfile(3L, request,
				bindingResult);

		assertEquals(409, response.getBody().getStatusCode());
		assertEquals("Email already exists", response.getBody().getMessage());
		assertEquals(false, response.getBody().isSuccess());
		assertEquals(null, response.getBody().getData());
	}

	// Test updateProfile unexpected exception
	@Test
	void testUpdateProfile_UnexpectedException() {
		EditProfileRequest request = createValidEditProfileRequest();

		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		when(userProfileService.updateProfile(eq(4L), any(EditProfileRequest.class)))
				.thenThrow(new RuntimeException("Something bad happened"));

		ResponseEntity<ApiResponse<UserProfileResponse>> response = userProfileController.updateProfile(4L, request,
				bindingResult);

		assertEquals(500, response.getBody().getStatusCode());
		assertEquals("An unexpected error occurred", response.getBody().getMessage());
		assertEquals(false, response.getBody().isSuccess());
		assertEquals(null, response.getBody().getData());
	}

	// Helper method to create a valid EditProfileRequest with sample values
	private EditProfileRequest createValidEditProfileRequest() {
		EditProfileRequest req = new EditProfileRequest();
		req.setProfileImg("http://example.com/profile.jpg");
		req.setName("Jane Doe");
		req.setUsername("janedoe");
		req.setEmail("jane.doe@example.com");
		req.setPhoneNumber("9876543210");
		req.setDob("01/01/1990");
		req.setIdentity("ID12345");
		req.setMarried("No");
		req.setAnniversaryDate("01/01/2010");
		req.setPincode("123456");
		req.setAddressLine1("123 Main St");
		req.setAddressLine2("Apt 4B");
		req.setCity("New York");
		req.setState("NY");
		return req;
	}
}
