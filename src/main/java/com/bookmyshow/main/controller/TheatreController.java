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

import com.bookmyshow.main.dto.TheatreDto;
import com.bookmyshow.main.service.TheatreService;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/theatre")
public class TheatreController {

	@Autowired
	private TheatreService theatreService;
	

	@PostMapping("/createTheatre")
	public ResponseEntity<TheatreDto> createTheatre(@RequestBody TheatreDto theatreDto){
        TheatreDto created = theatreService.createTheatre(theatreDto);
        return ResponseEntity.ok(created);
	}
    @GetMapping("/getAll")
    public ResponseEntity<List<TheatreDto>> getAllTheatres() {
        return ResponseEntity.ok(theatreService.getAllTheatres());
    }

    @GetMapping("/getByName")
    public ResponseEntity<List<TheatreDto>> getTheatreByName(@RequestParam String name) {
        List<TheatreDto> theatres = theatreService.getTheatresByName(name);
        if (theatres.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(theatres);
    }

    @PatchMapping("/{id}/delete")
    public ResponseEntity<Void> softDeleteTheatre(@PathVariable Long id) {
        if (theatreService.softDeleteTheatre(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
