package com.bookmyshow.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.dto.CityDTO;
import com.bookmyshow.main.service.CityService;
import com.bookmyshow.main.response.ApiResponse;

@RestController
@RequestMapping("/api/city")
public class CityController {

	@Autowired
	private CityService cityService;

	@GetMapping("/all")
	public ResponseEntity<ApiResponse<List<CityDTO>>>getAllCities() {

		
		return ResponseEntity.ok(
	            new ApiResponse<>(
	                HttpStatus.OK.value(),
	                "Fetch All Cities successfully",
	                true,
	                cityService.getAllCities()
	            )
	            );
	}

	@GetMapping("/popular")
	public ResponseEntity<ApiResponse<List<CityDTO>>> getPopularCities() {

		

		return ResponseEntity.ok(
	            new ApiResponse<>(
	                HttpStatus.OK.value(),
	                "Fetch Popular Cities successfully",
	                true,
	                cityService.getPopularCities()
	            )
	            );
	}
}
