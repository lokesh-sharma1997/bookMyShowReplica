package com.bookmyshow.main.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "languages")
public class Languages {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long languageId;

	@Column(nullable = false, unique = true)
	private String languageName;

}
