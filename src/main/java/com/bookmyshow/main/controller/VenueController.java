package com.bookmyshow.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.dto.VenueDto;
import com.bookmyshow.main.service.VenueService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping("/venue")
public class VenueController {

	@Autowired
	private VenueService venueService;
	

	@PostMapping("/createVenue")
	public ResponseEntity<VenueDto> createVenue(@RequestBody VenueDto theatreDto){
		VenueDto created = venueService.createVenue(theatreDto);
        return ResponseEntity.ok(created);
	}
    @GetMapping("/getAll")
    public ResponseEntity<List<VenueDto>> getAllVenues() {
        return ResponseEntity.ok(venueService.getAllVenues());
    }

    @GetMapping("venue/getByCity")
    public ResponseEntity<List<VenueDto>> getVenuesByCity(@RequestParam String name) {
        List<VenueDto> venues = venueService.getVenuesByCity(name);
        if (venues.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(venues);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<Void> softDeleteVenue(@PathVariable Long id) {
        if (venueService.softDeleteVenue(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
