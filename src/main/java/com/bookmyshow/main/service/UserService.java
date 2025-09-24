package com.bookmyshow.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.bookmyshow.main.dto.UserDTO;

public interface UserService {
	Optional<UserDTO> getByUserId(long userId);

	Optional<UserDTO> getByUsername(String username);

	List<UserDTO> getByRole(String roleName);

	public Page<UserDTO> searchUser(String value, int page, int size);

    Page<UserDTO> getAllUsers(int page, int size);

	boolean deleteById(long userId);

	void updateUserRole(long userId);

}
