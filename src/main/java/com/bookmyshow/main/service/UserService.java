package com.bookmyshow.main.service;

import java.util.List;
import java.util.Optional;

import com.bookmyshow.main.dto.UserDTO;

public interface UserService {
	Optional<UserDTO> getByUserId(long userId);

	Optional<UserDTO> getByUsername(String username);

	List<UserDTO> getByRole(String roleName);

	public List<UserDTO> searchUser(String value);

	List<UserDTO> getAllUsers();

	boolean deleteById(long userId);

	void updateUserRole(long userId);

}
