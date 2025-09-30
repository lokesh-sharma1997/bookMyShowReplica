package com.bookmyshow.main.Controller;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bookmyshow.main.controller.AuthController;
import com.bookmyshow.main.dto.LoginRequest;
import com.bookmyshow.main.dto.RegisterRequest;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.response.TokenResponse;
import com.bookmyshow.main.service.AuthService;
import com.bookmyshow.main.service.TokenService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private TokenService tokenService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private AuthController authController;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");
    }

    @Test
    void testLogin_Success() {
        String mockToken = "mocked.jwt.token";
        when(authService.login(loginRequest)).thenReturn(mockToken);

        ResponseEntity<ApiResponse<TokenResponse>> response = authController.login(loginRequest);

        assertThat(response.getBody().getStatusCode()).isEqualTo(200);
        assertThat(response.getBody().isSuccess()).isTrue();
        assertThat(response.getBody().getData().getToken()).isEqualTo(mockToken);
    }

    @Test
    void testRegister_WithValidationErrors() {
        FieldError error = new FieldError("registerRequest", "email", "Email is invalid");
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(error));

        ResponseEntity<ApiResponse<String>> response = authController.register(registerRequest, bindingResult);

        assertThat(response.getBody().getStatusCode()).isEqualTo(400);
        assertThat(response.getBody().isSuccess()).isFalse();
        assertThat(response.getBody().getMessage()).contains("Email is invalid");
    }

    @Test
    void testRegister_Success() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(authService.register(registerRequest)).thenReturn("Registration successful");

        ResponseEntity<ApiResponse<String>> response = authController.register(registerRequest, bindingResult);

        assertThat(response.getBody().getStatusCode()).isEqualTo(201);
        assertThat(response.getBody().isSuccess()).isTrue();
        assertThat(response.getBody().getMessage()).isEqualTo("Registration successful");
    }

    @Test
    void testValidateUsername_Available() {
        when(authService.userExistsByUsername("newuser")).thenReturn(false);

        ResponseEntity<ApiResponse<Boolean>> response = authController.validateUsername("newuser");

        assertThat(response.getBody().getStatusCode()).isEqualTo(200);
        assertThat(response.getBody().isSuccess()).isTrue();
        assertThat(response.getBody().getData()).isFalse(); // false = username NOT taken
    }

    @Test
    void testValidateUsername_Taken() {
        when(authService.userExistsByUsername("existingUser")).thenReturn(true);

        ResponseEntity<ApiResponse<Boolean>> response = authController.validateUsername("existingUser");

        assertThat(response.getBody().getStatusCode()).isEqualTo(200);
        assertThat(response.getBody().isSuccess()).isFalse();
        assertThat(response.getBody().getData()).isTrue(); // true = username IS taken
    }

    @Test
    void testValidateToken_Valid() {
        when(tokenService.isTokenValid(1L)).thenReturn(true);

        ResponseEntity<ApiResponse<Boolean>> response = authController.validateToken(1L);

        assertThat(response.getBody().getStatusCode()).isEqualTo(200);
        assertThat(response.getBody().isSuccess()).isTrue();
        assertThat(response.getBody().getData()).isTrue();
    }

    @Test
    void testValidateToken_Invalid() {
        when(tokenService.isTokenValid(1L)).thenReturn(false);

        ResponseEntity<ApiResponse<Boolean>> response = authController.validateToken(1L);

        assertThat(response.getBody().getStatusCode()).isEqualTo(401);
        assertThat(response.getBody().isSuccess()).isFalse();
        assertThat(response.getBody().getData()).isFalse();
    }

    @Test
    void testLogout() {
        doNothing().when(tokenService).deleteTokenFromRedis(1L);

        ResponseEntity<ApiResponse<Void>> response = authController.logout(1L);

        assertThat(response.getBody().getStatusCode()).isEqualTo(200);
        assertThat(response.getBody().isSuccess()).isTrue();
        assertThat(response.getBody().getMessage()).contains("Logout successful");
    }
}
