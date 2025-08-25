package com.bookmyshow.main.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
@Entity
@Table(name = "theatre")
public class Theatre {
     
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @NotBlank(message = "Theatre name is required")
     private String name;
    
    //@NotBlank(message="location is required")
	private String loaction;
	
	private Boolean deleted = false;
    
    @NotBlank(message = "city is required")
    //ManyToOne()
	private String city;

	
	
}
