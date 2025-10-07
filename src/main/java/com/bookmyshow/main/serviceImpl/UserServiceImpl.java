package com.bookmyshow.main.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	// Converts UserMaster entity to UserDTO
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

	// Retrieves user by user Id
	@Override
	public Optional<UserDTO> getByUserId(long userId) {
		if (userId <= 0) {
			throw new IllegalArgumentException("Invalid user ID: ID must be greater than zero.");
		}

		return Optional.ofNullable(userRepository.findByUserId(userId)).map(this::convertToDTO).or(() -> {
			throw new UserNotFoundException("User not found with ID: " + userId);
		});
	}

	// Retrieves user by username
	@Override
	public Optional<UserDTO> getByUsername(String username) {
		if (username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null or empty.");
		}

		return Optional.ofNullable(userRepository.findByUsername(username)).map(this::convertToDTO);
	}

	// Get All Users
	@Override
	public Page<UserDTO> getAllUsers(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<UserMaster> usersPage = userRepository.findByDeleteFlagFalse(pageable);
		return usersPage.map(this::convertToDTO);
	}

	// Soft deletes a user by setting deleteFlag, throws if already deleted
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

	// Updates user role to Admin, validates current role and deletion status
	@Override
	public void updateUserRole(long userId) {
		if (userId <= 0) {
			throw new IllegalArgumentException("Invalid user ID: ID must be greater than zero.");
		}

		UserMaster user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		if ("ADMIN".equals(user.getRole().getRoleName())) {
			throw new IllegalArgumentException("User is already an admin. Cannot change role of admin.");
		}

		if (Boolean.TRUE.equals(user.getDeleteFlag())) {
			throw new InvalidCredentialsException("User account is deleted. Please contact support.");
		}

		Role role = roleRepository.findById(2).orElseThrow(() -> new RoleNotFoundException("Role not found: Admin"));

		user.setRole(role);
		user.setUpdatedOn(LocalDateTime.now());
		userRepository.save(user);
	}

	// Searches users globally by value
	@Override
	public Page<UserDTO> searchUser(String value, int page, int size) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException("Search value cannot be null or empty.");
		}

		Pageable pageable = PageRequest.of(page, size);
		Page<UserMaster> usersPage = userRepository.globalSearch(value, pageable);

		return usersPage.map(this::convertToDTO);
	}

	@Override
	public Page<UserDTO> getByRoleName(String roleName, int page, int size) {

		if (roleName == null || roleName.trim().isEmpty()) {
			throw new IllegalArgumentException("Role name cannot be null or empty.");
		}

		Role role = roleRepository.findByRoleName(roleName.toUpperCase())
				.orElseThrow(() -> new RoleNotFoundException("Role not found: " + roleName));

		Pageable pageable = PageRequest.of(page, size);
		Page<UserMaster> usersPage = userRepository.findByRoleAndDeleteFlagFalse(role, pageable);

		return usersPage.map(this::convertToDTO);
	}

}