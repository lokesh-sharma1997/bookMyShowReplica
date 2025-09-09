package com.bookmyshow.main.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupportedCategory {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
	
	 private String categoryname;
	
	 @ManyToMany(mappedBy = "supportedCategories")
	    private List<Venue> venues;
}
