package com.bookmyshow.main.dto;

import com.bookmyshow.main.model.Role.RoleName;

import lombok.Data;
@Data
public class RegisterRequest {
    private String name;       // full name of the user
    private String username;   // unique username
    private String password;   // raw password (will be encoded before saving)
    private String email;      // user email
    private RoleName roleName;   // ADMIN / USER / etc
    private long phoneNumber;
}
