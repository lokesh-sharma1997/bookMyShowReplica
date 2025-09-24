package com.bookmyshow.main.controller;

import com.bookmyshow.main.dto.VenueDTO;
import com.bookmyshow.main.exception.UserNotFoundException;
import com.bookmyshow.main.exception.VenueNotFoundException;
import com.bookmyshow.main.response.ApiResponse;
import com.bookmyshow.main.service.VenueService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues") 
public class VenueController {

    @Autowired
    private VenueService venueService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Void>> createVenue(@RequestBody VenueDTO venueDto) {
         venueService.createVenue(venueDto);
        ApiResponse<Void> response = new ApiResponse<>(
	            HttpStatus.CREATED.value(),
	            "Venue created successfully",
	            true,
	            null
	    );

	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        List<VenueDTO> venues = venueService.getAllVenues();
        return ResponseEntity.ok(venues);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<VenueDTO>> getVenuesByCity(@PathVariable String city) {
        List<VenueDTO> venues = venueService.getVenuesByCity(city);
        return ResponseEntity.ok(venues);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> softDeleteVenue(@PathVariable Long id) {
        boolean deleted = venueService.softDeleteVenue(id);
        if (!deleted) {
			throw new VenueNotFoundException("Venue not found with id: " + id);
		}
        return ResponseEntity.ok(new ApiResponse<>(204, "Venue deleted successfully", true, "Venue with ID " + id + " deleted"));
    }
    
}
