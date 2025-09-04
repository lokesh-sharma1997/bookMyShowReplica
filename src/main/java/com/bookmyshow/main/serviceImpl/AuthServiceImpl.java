package com.bookmyshow.main.serviceImpl;

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
import com.bookmyshow.main.util.AESUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	@Value("${app.jwt.secret}")
	String secretKey;

	@Override
	public String login(LoginRequest req) {
		try {

			String decryptedPassword = AESUtil.decrypt(req.getPassword(), secretKey);
			// Authenticate credentials
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), decryptedPassword));

			// Fetch user to extract role
			UserMaster user = userRepository.findByUsername(req.getUsername());
			if (user == null || user.getRole() == null) {
				throw new UserNotFoundException("User or role not found for username: " + req.getUsername());
			}

			String role = user.getRole().getRoleName(); // Already a String
			Long userId = user.getUserId();
			// Generate token with role and return as string
			return jwtService.generateToken(req.getUsername(), role, userId);

		} catch (AuthenticationException ex) {
			throw new InvalidCredentialsException("Invalid credentials for username: " + req.getUsername());
		} catch (Exception e) {
			throw new RuntimeException("Failed to decrypt password or login: " + e.getMessage(), e);
		}
	}

	@Override
	public String register(RegisterRequest req) {
		if (userRepository.existsByUsername(req.getUsername())) {
			throw new ResourceAlreadyExistsException("Username already taken: " + req.getUsername());
		}
 try {
	 String decryptedPassword=AESUtil.decrypt(req.getPassword(), secretKey);
		UserMaster user = new UserMaster();
		user.setName(req.getName());
		user.setUsername(req.getUsername());
		user.setPassword(passwordEncoder.encode(req.getPassword()));
		user.setEmail(req.getEmail());
		user.setPhoneNumber(req.getPhoneNumber());

		// Fetch and assign role
		Role role = roleRepository.findByRoleName("USER")
				.orElseThrow(() -> new RoleNotFoundException("Default role USER not found"));
		user.setRole(role);

		userRepository.save(user);

		return "User registered successfully";
	}
 catch(Exception e) {
	 throw new RuntimeException("Password decryption failing during registration"+e.getMessage(),e);
       }
	}
}
