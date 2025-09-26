package com.bookmyshow.main.service;

import com.bookmyshow.main.dto.TimeSlotDTO;
import com.bookmyshow.main.dto.VenueDTO;
import com.bookmyshow.main.model.Venue;

import java.time.LocalDate;
import java.util.List;

public interface VenueService {

    VenueDTO createVenue(VenueDTO dto);

    List<VenueDTO> getAllVenues();

    List<VenueDTO> getVenuesByCity(String city);

    boolean softDeleteVenue(Long id);


    List<TimeSlotDTO> getAvailableTimeSlots(Long venueId, Long screenId, LocalDate date);

	VenueDTO updateVenue(Long venueId, VenueDTO dto);
    
}
