package com.bookmyshow.main.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "user_master")
public class UserMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(nullable = false)
	private String name;

	@Column(unique = true, updatable = false, nullable = false) // Unique and non-updatable field
	private String username;
	
	@Column(nullable = false)
	@Size(min = 8, message = "Password must be at least 8 characters long")
	private String password; // Size(min = 8, message = "Password must be at least 8 characters long")

	@Column(unique = true, nullable = false)
	private String email;

	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "role_id")
	@Enumerated(EnumType.STRING)
	private Role role;

	 
	@Size(max=10,  message="Mobile number cannot contain more than ten characters")
	private String phoneNumber;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdOn;

	@UpdateTimestamp
	private LocalDateTime updatedOn;
	
	private Boolean deleteFlag = false;
}
