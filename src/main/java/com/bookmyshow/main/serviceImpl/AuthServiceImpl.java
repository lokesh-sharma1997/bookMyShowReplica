package com.bookmyshow.main.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.LoginRequest;
import com.bookmyshow.main.dto.RegisterRequest;
import com.bookmyshow.main.exception.InvalidCredentialsException;
import com.bookmyshow.main.exception.ResourceAlreadyExistsException;
import com.bookmyshow.main.exception.RoleNotFoundException;
import com.bookmyshow.main.exception.UserNotFoundException;
import com.bookmyshow.main.model.Role;
import com.bookmyshow.main.model.UserMaster;
import com.bookmyshow.main.repository.RoleRepository;
import com.bookmyshow.main.repository.UserRepository;
import com.bookmyshow.main.security.JwtService;
import com.bookmyshow.main.service.AuthService;
import com.bookmyshow.main.service.TokenService;
import com.bookmyshow.main.util.AESUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final TokenService tokenService;

	@Value("${app.jwt.secret}")
	String secretKey;

	@Value("${app.jwt.expiration-ms}")
	private long ttl;

	// Handles user login by authenticating and generating JWT token
	@Override
	public String login(LoginRequest req) {
		try {
			// Decrypt incoming encrypted password
			String decryptedPassword = AESUtil.decrypt(req.getPassword(), secretKey);

			// Authenticate user credentials
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), decryptedPassword));

			// Load user to get role and status
			UserMaster user = userRepository.findByUsername(req.getUsername());
			if (user == null || user.getRole() == null) {
				throw new UserNotFoundException("User or role not found for username: " + req.getUsername());
			}
			if (Boolean.TRUE.equals(user.getDeleteFlag())) {
				throw new InvalidCredentialsException("User account is deleted . Please contact support.");
			}

			// Generate JWT token with user information
			String token = jwtService.generateToken(req.getUsername(), user.getRole().getRoleName(), user.getUserId());

			// Save token with expiration time
			tokenService.saveToken(token, user.getUserId(), ttl);

			return token;

		} catch (AuthenticationException ex) {
			throw new InvalidCredentialsException("Invalid credentials for username: " + req.getUsername());
		} catch (Exception e) {
			throw new RuntimeException("Failed to decrypt password or login: " + e.getMessage(), e);
		}
	}

	// Handles new user registration with encrypted password and default USER role
	@Override
	public String register(RegisterRequest req) {
		if (userRepository.existsByUsername(req.getUsername())) {
			throw new ResourceAlreadyExistsException("Username already taken: " + req.getUsername());
		}
		if (userRepository.existsByEmailAndDeleteFlag(req.getEmail(), false)) {
			throw new ResourceAlreadyExistsException("Email already taken: " + req.getEmail());
		}
		try {
			// Decrypt password from request
			String decryptedPassword = AESUtil.decrypt(req.getPassword(), secretKey);

			UserMaster user = new UserMaster();
			user.setName(req.getName());
			user.setUsername(req.getUsername());
			user.setPassword(passwordEncoder.encode(decryptedPassword));
			user.setEmail(req.getEmail());
			user.setPhoneNumber(req.getPhoneNumber());

			// Assign default USER role to new user
			Role role = roleRepository.findByRoleName("USER")
					.orElseThrow(() -> new RoleNotFoundException("Default role USER not found"));
			user.setRole(role);

			userRepository.save(user);

			return "User registered successfully";
		} catch (Exception e) {
			throw new RuntimeException("Password decryption failing during registration" + e.getMessage(), e);
		}
	}

	// Checks if a user exists by username
	@Override
	public boolean userExistsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
}
