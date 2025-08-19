package com.bookmyshow.main.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookmyshow.main.model.City;
import com.bookmyshow.main.service.CityService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CityController {

	@Autowired
    private  CityService cityService;

    

    @GetMapping("/cities/all")
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/api/cities/popular")
    public List<City> getPopularCities() {
        return cityService.getPopularCities();
    }
}
