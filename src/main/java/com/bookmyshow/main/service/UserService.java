package com.bookmyshow.main.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.bookmyshow.main.dto.UserDTO;
import com.bookmyshow.main.model.User;


public interface UserService
{
	UserDTO getByUserIdUser(int userId);
	
	List<UserDTO> getByNameUser(String name);

	UserDTO getByUsernameUser(String username);
	
	UserDTO getByEmailUser(String email);
	
	List<UserDTO> getByRole(String role);
	
	UserDTO getByPhoneNumberUser(long phoneNumber);
	
	List<UserDTO> getByCreatedOnDateUser(LocalDate localDate);
	
	List<UserDTO> getByUpdatedOnDateUser(LocalDate localDate);
	
	List<UserDTO> getAllUser();

	Map<String, Object> createUser(UserDTO userDTO);

	UserDTO deleteByIdUser(int userId);
}
