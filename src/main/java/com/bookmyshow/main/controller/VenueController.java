package com.bookmyshow.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.dto.VenueDto;
import com.bookmyshow.main.service.VenueService;

@RestController
@RequestMapping("/venue")
public class VenueController {

	@Autowired
	private VenueService venueService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create-venue")
	public ResponseEntity<VenueDto> createTheatre(@RequestBody VenueDto theatreDto){
		VenueDto created = venueService.createVenue(theatreDto);
        return ResponseEntity.ok(created);
	}

	
	@GetMapping("/getAll")
	public ResponseEntity<List<VenueDto>> getAllVenues() {
		return ResponseEntity.ok(venueService.getAllVenues());
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getByName")
	public ResponseEntity<List<VenueDto>> getVenueByName(@RequestParam String name) {
		List<VenueDto> theatres = venueService.getVenuesByName(name);
		if (theatres.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(theatres);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/delete/{id}")
	public ResponseEntity<Void> softDeleteTheatre(@PathVariable Long id) {
		if (venueService.softDeleteVenue(id)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
