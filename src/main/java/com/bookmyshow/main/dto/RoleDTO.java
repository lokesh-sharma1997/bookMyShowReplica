package com.bookmyshow.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

	private int roleId;

	@JsonProperty("roleName")

	@NotBlank(message = "Role name is required")

	@Size(min = 3, max = 20, message = "Role name must be between 3 and 20 characters")

	private String roleName;

}
