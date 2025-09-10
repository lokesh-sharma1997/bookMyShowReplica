package com.bookmyshow.main.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.UserDTO;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

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

	// Convert DTO -> Entity
	private UserMaster convertToEntity(UserDTO dto) {
		UserMaster user = new UserMaster();
		user.setUserId(dto.getUserId());
		user.setName(dto.getName());
		user.setUsername(dto.getUsername());
		user.setPassword(dto.getPassword());
		user.setEmail(dto.getEmail());
		user.setPhoneNumber(dto.getPhoneNumber());

		if (dto.getRoleName() != null) {
			Role role = roleRepository.findByRoleName(dto.getRoleName().toUpperCase())
					.orElseThrow(() -> new RoleNotFoundException("Invalid role: " + dto.getRoleName()));
			user.setRole(role);
		}

		user.setCreatedOn(dto.getCreatedOn());
		user.setUpdatedOn(dto.getUpdatedOn());
		user.setDeleteFlag(dto.getDeleteFlag() != null ? dto.getDeleteFlag() : false);

		return user;
	}

	@Override
	public Optional<UserDTO> getByUserId(int userId) {
		return Optional.ofNullable(userRepository.findByUserId(userId)).map(this::convertToDTO).or(() -> {
			throw new UserNotFoundException("User not found with ID: " + userId);
		});
	}

	@Override
	public Optional<UserDTO> getByUsername(String username) {
		return Optional.ofNullable(userRepository.findByUsername(username)).map(this::convertToDTO);
	}

	@Override
	public List<UserDTO> getByRole(String roleName) {
		Role role = roleRepository.findByRoleName(roleName.toUpperCase())
				.orElseThrow(() -> new RoleNotFoundException("Role not found: " + roleName));
		return userRepository.findByRole(role).stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<UserDTO> getAllUsers() {
		return userRepository.findAll().stream().filter(user -> !user.getDeleteFlag()).map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public boolean deleteById(int userId) {
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
	public void updateUserRole(int userId ) {
		UserMaster user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		Role role = roleRepository.findById(2)
				.orElseThrow(() -> new RoleNotFoundException("Role not found: Admin"));

		user.setRole(role);
		user.setUpdatedOn(LocalDateTime.now());

		userRepository.save(user);
	}

	@Override
	public List<UserDTO> searchUser(String value) {
		return userRepository.globalSearch(value).stream().map(this::convertToDTO).collect(Collectors.toList());
	}

}
