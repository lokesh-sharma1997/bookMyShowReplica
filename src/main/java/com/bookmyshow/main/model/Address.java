package com.bookmyshow.main.model;
 
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

	@Id
	private Long id;
	
    private String street;

    private String city;

    private String pin;
}
