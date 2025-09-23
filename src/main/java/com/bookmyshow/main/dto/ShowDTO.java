package com.bookmyshow.main.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO {
	 private Long showId;
	    private LocalTime time;          
	    private String venueName;
	    private List<ShowCategoryDTO> showcategories;
		
}
