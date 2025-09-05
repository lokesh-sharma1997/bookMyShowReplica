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
@Table(name = "format")
@Data
public class Format {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long formatId;
	
	@Column(nullable = false, unique = true)
    private String formatName;
	
	@ManyToMany(mappedBy = "format")
	private List<Event> events;


}
