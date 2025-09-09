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
@Table(name = "genres")
@Data
public class Genres {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long genreId;

	@Column(nullable = false, unique = true)
	private String genresName;
	
	@ManyToMany(mappedBy = "genres")
	private List<Event> events;


}
