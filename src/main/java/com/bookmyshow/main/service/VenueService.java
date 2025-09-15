package com.bookmyshow.main.service;

import com.bookmyshow.main.dto.VenueDTO;
import com.bookmyshow.main.model.Venue;

import java.util.List;

public interface VenueService {

    VenueDTO createVenue(VenueDTO dto);

    List<VenueDTO> getAllVenues();

    List<VenueDTO> getVenuesByCity(String city);

    boolean softDeleteVenue(Long id);
    
//    List<Venue> getByVenueFor(String venuefor);
}
