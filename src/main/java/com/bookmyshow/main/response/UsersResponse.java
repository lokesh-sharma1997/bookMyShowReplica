package com.bookmyshow.main.response;

import com.bookmyshow.main.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UsersResponse {
    private List<UserDTO> users;
}
