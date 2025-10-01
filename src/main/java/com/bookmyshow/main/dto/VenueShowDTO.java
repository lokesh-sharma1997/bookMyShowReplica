package com.bookmyshow.main.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueShowDTO {
	 private String venueName;
	    private String venueId;
	    private String screenId;
	    private String showId; 
	    private List<ShowFetchDTO> shows;
}
