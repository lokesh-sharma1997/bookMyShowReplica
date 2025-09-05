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
@Table(name = "categories")
@Data
public class Categories {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
	
	@Column(nullable = false, unique = true)
    private String categoriesName;
	
	@ManyToMany(mappedBy = "categories")
	private List<Event> events;

}
