package com.bookmyshow.main.dto;

import com.bookmyshow.main.config.PastDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditProfileRequest {

	private String profileImg;

	@Size(min = 1, max = 100, message = "Name cannot be empty and should be within 255 characters")
	private String name;

	private String username;

	@Email(message = "Email should be a valid email address")
	private String email;

	@Size(min = 10, max = 10, message = "Phone number should be exactly 10 characters")
	@Pattern(regexp = "^\\d{10}$", message = "Phone number should contain exactly 10 digits and no other characters")
	private String phoneNumber;

	@Pattern(regexp = "^(0?[1-9]|1[0-2])/(0?[1-9]|[12][0-9]|3[01])/(\\d{4})$", message = "Date of Birth should be in the format MM/DD/YYYY")
	@PastDate(message = "Date of Birth should be in the past")
	private String dob;

	private String identity;

	private String married;
	@Pattern(regexp = "^(0?[1-9]|1[0-2])/(0?[1-9]|[12][0-9]|3[01])/(\\d{4})$", message = "Anniversary Date should be in the format MM/DD/YYYY")
	@PastDate(message = "Anniversary date should be in the past")
	private String anniversaryDate;

	@Pattern(regexp = "^[0-9]{6}$", message = "Pincode should be a valid 6-digit number")
	private String pincode;

	@Size(max = 100, message = "Address Line 1 should not exceed 255 characters")
	private String addressLine1;

	@Size(max = 100, message = "Address Line 2 should not exceed 255 characters")
	private String addressLine2;

	@Size(max = 25, message = "City should not exceed 100 characters")
	private String city;

	@Size(max = 25, message = "State should not exceed 100 characters")
	private String state;

}
