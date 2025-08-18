package com.bookmyshow.main.dto;

import java.time.LocalDateTime;

import com.bookmyshow.main.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
	
	private int userId;
	
	@JsonProperty("name")
	@NotBlank(message = "Name is required")
    private String name;
	
    private String username;
    
    @JsonProperty("email")
	@NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @JsonProperty("phone_number")
	@Min(value = 1000000000L, message = "Phone number must be at least 10 digits")
    @Max(value = 9999999999L, message = "Phone number must be at most 10 digits")
    private long phoneNumber;
    
    @JsonProperty("role_name")
    @NotBlank(message = "Role name is required")
    private String roleName;

    @JsonProperty("created_On")
    private LocalDateTime createdOn;
    
    @JsonProperty("updated_On")
    private LocalDateTime updatedOn;
    
    @JsonProperty("delete_flag")
    private Boolean deleteFlag;

}
