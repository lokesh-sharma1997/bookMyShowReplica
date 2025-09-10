package com.bookmyshow.main.dto;


import java.util.List;

import lombok.Data;

@Data
public class VenueDTO {

    private Long id;
    private String venueName;
    private int venueCapacity;
    private String venueFor;
    private String venueType;
    
    
    private AddressDTO address;  
    private List<AmenityDTO> amenities; 
//    private List<Integer> amenities;
    private List<SupportedCategoryDTO> supportedCategories;  
    private List<ScreenDTO> screens;  
    private Boolean deleted;  

}
