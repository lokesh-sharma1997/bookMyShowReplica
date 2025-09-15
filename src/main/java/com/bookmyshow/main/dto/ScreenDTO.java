package com.bookmyshow.main.dto;

import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
public class ScreenDTO {
	
	
	@JsonIgnore
    private Long id;
	
    private String screenName;
    private List<LayoutDTO> layouts; 

}
