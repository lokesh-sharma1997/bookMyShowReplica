package com.bookmyshow.main.response;

import com.bookmyshow.main.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
	private UserDTO user;
}
