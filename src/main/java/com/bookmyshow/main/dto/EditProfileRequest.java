package com.bookmyshow.main.dto;

import com.bookmyshow.main.config.PastDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class EditProfileRequest {

	@Schema(defaultValue = "string")
	private String profileImg;

	@Size(min = 1, max = 100, message = "Name cannot be empty and should be within 255 characters")
	@Schema(defaultValue = "string")
	private String name;

	@Schema(defaultValue = "string")
	private String username;

	@Email(message = "Email should be a valid email address")
	@Schema(defaultValue = "user@example.com")
	private String email;

	@Size(min = 10, max = 10, message = "Phone number should be exactly 10 characters")
	@Pattern(regexp = "^\\d{10}$", message = "Phone number should contain exactly 10 digits and no other characters")
	@Schema(defaultValue = "0000000000")
	private String phoneNumber;

	@PastDate(message = "Date of Birth should be in the past")
	@Schema(defaultValue = "01/01/1990")
	private String dob;

	@Schema(defaultValue = "string")
	private String identity;

	@Schema(defaultValue = "string")
	private String married;

	@PastDate(message = "Anniversary date should be in the past")
	@Schema(defaultValue = "01/01/1990")
	private String anniversaryDate;

	@Pattern(regexp = "^[0-9]{6}$", message = "Pincode should be a valid 6-digit number")
	@Schema(defaultValue = "000000")
	private String pincode;

	@Size(max = 100, message = "Address Line 1 should not exceed 255 characters")
	@Schema(defaultValue = "string")
	private String addressLine1;

	@Size(max = 100, message = "Address Line 2 should not exceed 255 characters")
	@Schema(defaultValue = "string")
	private String addressLine2;

	@Size(max = 25, message = "City should not exceed 100 characters")
	@Schema(defaultValue = "string")
	private String city;

	@Size(max = 25, message = "State should not exceed 100 characters")
	@Schema(defaultValue = "string")
	private String state;
}
