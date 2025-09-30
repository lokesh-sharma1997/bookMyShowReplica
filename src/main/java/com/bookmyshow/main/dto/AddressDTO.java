
package com.bookmyshow.main.dto;

import com.bookmyshow.main.model.City;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Data
public class AddressDTO {

	@JsonIgnore
    private Long id;
    private String street;
    private CityVDTO city;
    private String pin;
	

}
