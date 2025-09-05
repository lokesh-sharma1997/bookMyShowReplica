package com.bookmyshow.main.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "release_month")
@Data
public class ReleaseMonth {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long releaseMonthId;

	@Column(nullable = false, unique = true)
	private String releaseMonthName;

	@ManyToMany(mappedBy = "releaseMonth")
	private List<Event> events;
}
