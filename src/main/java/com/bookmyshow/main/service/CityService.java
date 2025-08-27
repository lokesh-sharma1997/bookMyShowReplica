package com.bookmyshow.main.service;


import java.util.List;

import com.bookmyshow.main.dto.CityDto;

public interface CityService {

    List<CityDto> getAllCities();

    List<CityDto> getPopularCities();
    
    
}

