package com.bookmyshow.main.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
	
	private int userId;
	
	@JsonProperty("name")
	@NotBlank(message = "Name is required")
    private String name;
	
	@NotBlank(message = "Username is required")
    private String username;
	
	@Size(min = 8, message = "Password must be at least 8 characters long")
    @JsonProperty("password")
	private String password;
    
    @JsonProperty("email")
	@NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @JsonProperty("phoneNumber")
	@Min(value = 1000000000L, message = "Phone number must be at least 10 digits")
    @Max(value = 9999999999L, message = "Phone number must be at most 10 digits")
    private long phoneNumber;
    
    @JsonProperty("roleName")
    @NotBlank(message = "Role name is required")
    private String roleName;

    @JsonProperty("createdOn")
    private LocalDateTime createdOn;
    
    @JsonProperty("updatedOn")
    private LocalDateTime updatedOn;
    
    @JsonProperty("deleteFlag")
    private Boolean deleteFlag;

 

}
