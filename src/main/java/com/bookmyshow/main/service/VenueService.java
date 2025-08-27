package com.bookmyshow.main.service;


import java.util.List;

import com.bookmyshow.main.dto.VenueDto;

public interface VenueService {

	VenueDto createVenue(VenueDto dto);

    List<VenueDto> getAllVenues();

    List<VenueDto> getVenuesByName(String name);

   

	boolean softDeleteVenue(Long id);
}
