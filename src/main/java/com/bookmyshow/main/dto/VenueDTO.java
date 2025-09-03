package com.bookmyshow.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueDTO {
    
    private Long id; 

    private String venueName;

    private AddressDTO address;

    private int venueCapacity;

    private String venueFor;  

    private String venueType;

    private Set<String> supportedCategories;

    private Map<String, String> additionalFields;

    private Boolean deleted = false;
}
