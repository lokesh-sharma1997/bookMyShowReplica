package com.bookmyshow.main.service;


import java.util.List;

import com.bookmyshow.main.dto.CityDto;
import com.bookmyshow.main.model.City;

public interface CityService {

    List<CityDto> getAllCities();

    List<CityDto> getPopularCities();
}

