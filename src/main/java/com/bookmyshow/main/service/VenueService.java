package com.bookmyshow.main.service;

import com.bookmyshow.main.dto.TimeSlotDTO;
import com.bookmyshow.main.dto.VenueDTO;
import com.bookmyshow.main.events.NotificationEvent;
import com.bookmyshow.main.model.Address;
import com.bookmyshow.main.model.City;
import com.bookmyshow.main.model.Layout;
import com.bookmyshow.main.model.LayoutRow;
import com.bookmyshow.main.model.Screen;
import com.bookmyshow.main.model.Seat;
import com.bookmyshow.main.model.Venue;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

public interface VenueService {

	VenueDTO createVenue(VenueDTO dto);

	List<VenueDTO> getVenuesByCity(String city);

	boolean softDeleteVenue(Long id);

	VenueDTO updateVenue(Long venueId, VenueDTO dto);

	List<TimeSlotDTO> getAvailableTimeSlots(Long venueId, Long screenId, LocalDate date);

	VenueDTO getVenueById(Long id);

	Map<String, Object> getAllVenues(int page, int size);

}
