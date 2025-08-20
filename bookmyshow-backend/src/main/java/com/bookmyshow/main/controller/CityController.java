package com.bookmyshow.main.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.dto.CityDto;
import com.bookmyshow.main.service.CityService;

@RestController
@RequestMapping("/api")
public class CityController {

	@Autowired
    private  CityService cityService;

    

    @GetMapping("/cities/all")
    public List<CityDto> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/api/cities/popular")
    public List<CityDto> getPopularCities() {
        return cityService.getPopularCities();
    }
}
