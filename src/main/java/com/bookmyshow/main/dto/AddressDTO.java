
package com.bookmyshow.main.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Data
public class AddressDTO {

	@JsonIgnore
    private Long id;
    private String street;
    private String city;
    private String pin;

}
