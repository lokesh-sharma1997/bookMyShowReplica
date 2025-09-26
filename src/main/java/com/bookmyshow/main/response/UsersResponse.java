package com.bookmyshow.main.response;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import com.bookmyshow.main.dto.UserDTO;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UsersResponse {
	public UsersResponse(List<UserDTO> users) {
		 this.users=users;
	}
	private List<UserDTO> users;
	private long totalEntries;

}
