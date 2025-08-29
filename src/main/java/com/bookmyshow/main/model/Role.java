package com.bookmyshow.main.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "app_role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private int roleId;

	@Column(nullable = false, unique = true)
	private String roleName; // e.g., "ADMIN", "USER", "MANAGER", etc.

	@OneToMany(mappedBy = "role")
	private List<UserMaster> users;

}
