package com.bookmyshow.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleDTO {

    private int roleId;

    @JsonProperty("role_name")
    @NotBlank(message = "Role name is required")
    private String roleName;
}
