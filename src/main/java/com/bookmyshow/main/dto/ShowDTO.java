package com.bookmyshow.main.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
public class ShowDTO {

	 private Long showid;
	    private Long venue;  
	    private Long screen; 
	    private Long layout; 
	    private Integer showPrice;
	    private List<ShowTimeDTO> showtimesdate;
}