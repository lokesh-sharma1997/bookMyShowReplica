package com.bookmyshow.main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "user_profile")
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_id")
	private Long profileId;

	private String profileImg; // base64 encoded string

	private String dob;
	private String identity; // "Man" / "Woman"
	private String married; // "Yes" / "No"
	private String anniversaryDate;

	private String pincode;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	@ToString.Exclude
    @EqualsAndHashCode.Exclude
	private UserMaster user;
}