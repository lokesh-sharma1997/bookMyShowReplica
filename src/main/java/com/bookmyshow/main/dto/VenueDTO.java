package com.bookmyshow.main.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class VenueDTO {

	@JsonIgnore 
    private Long id;
	
    private String venueName;
    private int venueCapacity;
//    private String venueFor;
    private String venueType;
    
    
    private AddressDTO address;  
    private List<String> amenities; 
//    private List<Integer> amenities;
    private List<String> supportedCategories;  
    private List<ScreenDTO> screens; 
//    private List<TimeSlotDTO> timeSlots;

    
//    private Boolean deleted;  
    
}
