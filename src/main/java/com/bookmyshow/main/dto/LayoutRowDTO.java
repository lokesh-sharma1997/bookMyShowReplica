package com.bookmyshow.main.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class LayoutRowDTO {

	
	@JsonIgnore
    private Long id;
    private String rowName;
    private Long layoutId;  

}
