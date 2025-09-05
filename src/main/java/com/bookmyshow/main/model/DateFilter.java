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
@Table(name = "date_filter")
@Data
public class DateFilter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dateFilterId;

	@Column(nullable = false, unique = true)
	private String dateFilterName;
	
	@ManyToMany(mappedBy = "dateFilter")
	private List<Event> events;

}