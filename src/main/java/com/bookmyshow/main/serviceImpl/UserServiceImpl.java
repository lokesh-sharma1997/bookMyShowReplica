package com.bookmyshow.main.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.UserDTO;
import com.bookmyshow.main.exception.InvalidCredentialsException;
import com.bookmyshow.main.exception.RoleNotFoundException;
import com.bookmyshow.main.exception.UserNotFoundException;
import com.bookmyshow.main.model.Role;
import com.bookmyshow.main.model.UserMaster;
import com.bookmyshow.main.repository.RoleRepository;
import com.bookmyshow.main.repository.UserRepository;
import com.bookmyshow.main.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	// Convert Entity -> DTO
	private UserDTO convertToDTO(UserMaster user) {
		UserDTO dto = new UserDTO();
		dto.setUserId(user.getUserId());
		dto.setName(user.getName());
		dto.setUsername(user.getUsername());
		dto.setPassword(user.getPassword());
		dto.setEmail(user.getEmail());
		dto.setPhoneNumber(user.getPhoneNumber());
		dto.setRoleName(user.getRole() != null ? user.getRole().getRoleName() : null);
		dto.setCreatedOn(user.getCreatedOn());
		dto.setUpdatedOn(user.getUpdatedOn());
		dto.setDeleteFlag(user.getDeleteFlag());
		return dto;
	}

	@Override
	public Optional<UserDTO> getByUserId(long userId) {
		if (userId <= 0) {
			throw new IllegalArgumentException("Invalid user ID: ID must be greater than zero.");
		}

		return Optional.ofNullable(userRepository.findByUserId(userId)).map(this::convertToDTO).or(() -> {
			throw new UserNotFoundException("User not found with ID: " + "" + userId);
		});
	}

	@Override
	public Optional<UserDTO> getByUsername(String username) {
		if (username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null or empty.");
		}

		return Optional.ofNullable(userRepository.findByUsername(username)).map(this::convertToDTO);
	}

	@Override
	public List<UserDTO> getByRole(String roleName) {
		if (roleName == null || roleName.trim().isEmpty()) {
			throw new IllegalArgumentException("Role name cannot be null or empty.");
		}

		Role role = roleRepository.findByRoleName(roleName.toUpperCase())
				.orElseThrow(() -> new RoleNotFoundException("Role not found: " + roleName));

		return userRepository.findByRole(role).stream().filter(user -> !user.getDeleteFlag()).map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream().filter(user -> !user.getDeleteFlag()).map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public boolean deleteById(long userId) {
		if (userId <= 0) {
			throw new IllegalArgumentException("Invalid user ID: ID must be greater than zero.");
		}

		return userRepository.findById(userId).map(user -> {
			if (Boolean.TRUE.equals(user.getDeleteFlag())) {
				throw new RuntimeException("User already deleted with ID: " + userId);
			}

			user.setDeleteFlag(true);
			userRepository.save(user);
			return true;
		}).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
	}

	@Override
	public void updateUserRole(long userId) {
		if (userId <= 0) {
			throw new IllegalArgumentException("Invalid user ID: ID must be greater than zero.");
		}

		UserMaster user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
		if (user.getRole().getRoleName().equals("ADMIN")) {
			throw new IllegalArgumentException("User is already an admin. Cannot change role of admin.");	
		}
		if (Boolean.TRUE.equals(user.getDeleteFlag())) {
			throw new InvalidCredentialsException("User account is deleted. Please contact support.");
		}

		// Assuming "Admin" role is fixed with role ID = 2, add validation if needed
		Role role = roleRepository.findById(2).orElseThrow(() -> new RoleNotFoundException("Role not found: Admin"));

		user.setRole(role);
		user.setUpdatedOn(LocalDateTime.now());

		userRepository.save(user);
	}

	@Override
	public List<UserDTO> searchUser(String value) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException("Search value cannot be null or empty.");
		}

		return userRepository.globalSearch(value).stream().map(this::convertToDTO).collect(Collectors.toList());
	}
}
