package com.bookmyshow.main.service;

import com.bookmyshow.main.dto.VenueDTO;

import java.util.List;

public interface VenueService {

    VenueDTO createVenue(VenueDTO dto);

    List<VenueDTO> getAllVenues();

    List<VenueDTO> getVenuesByCity(String city);

    boolean softDeleteVenue(Long id);
}
