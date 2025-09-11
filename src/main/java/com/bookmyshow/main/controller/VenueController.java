package com.bookmyshow.main.controller;

import com.bookmyshow.main.dto.VenueDTO;
import com.bookmyshow.main.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues") 
public class VenueController {

    @Autowired
    private VenueService venueService;

    @PostMapping("/create")
    public ResponseEntity<VenueDTO> createVenue(@RequestBody VenueDTO venueDto) {
        VenueDTO created = venueService.createVenue(venueDto);
        return ResponseEntity.ok(created);
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
    public ResponseEntity<Void> softDeleteVenue(@PathVariable Long id) {
        boolean deleted = venueService.softDeleteVenue(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
    
}
