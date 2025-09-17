package com.bookmyshow.main.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EditProfileRequest {

	@Size(max = 255, message = "Profile image URL should not exceed 255 characters")
	private String profileImg;

	@Size(min = 1, max = 255, message = "Name cannot be empty and should be within 255 characters")
	private String name;

	@Size(min = 3, max = 50, message = "Username should be between 3 and 50 characters")
	private String username;

	@Email(message = "Email should be a valid email address")
	private String email;

	@Size(min = 10, max = 10, message = "Phone number should be exactly 10 characters")
	@Pattern(regexp = "^\\d{10}$", message = "Phone number should contain exactly 10 digits")
	private String phoneNumber;

	private String dob;

	@Size(max = 255, message = "Identity field should not exceed 255 characters")
	private String identity;

	private String married;

	private String anniversaryDate;

	@Pattern(regexp = "^[0-9]{6}$", message = "Pincode should be a valid 6-digit number")
	private String pincode;

	@Size(max = 255, message = "Address Line 1 should not exceed 255 characters")
	private String addressLine1;

	@Size(max = 255, message = "Address Line 2 should not exceed 255 characters")
	private String addressLine2;

	@Size(max = 100, message = "City should not exceed 100 characters")
	private String city;

	@Size(max = 100, message = "State should not exceed 100 characters")
	private String state;

	@Size(max = 100, message = "Country should not exceed 100 characters")
	private String country;
}
