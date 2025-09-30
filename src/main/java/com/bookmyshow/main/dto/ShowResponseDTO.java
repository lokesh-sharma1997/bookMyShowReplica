package com.bookmyshow.main.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowResponseDTO {
	
	    private int status;
	    private String message;
	    private List<VenueShowDTO> data;
	
}
