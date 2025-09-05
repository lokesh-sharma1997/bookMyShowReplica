package com.bookmyshow.main.service;


import java.util.List;

import com.bookmyshow.main.dto.CityDTO;

public interface CityService {

    List<CityDTO> getAllCities();

    List<CityDTO> getPopularCities();
}

