package com.bookmyshow.main.service;


import java.util.List;

import com.bookmyshow.main.dto.CityDTO;
import com.bookmyshow.main.response.CityResponseDto;

public interface CityService {

    List<CityResponseDto> getAllCities();

    List<CityDTO> getPopularCities();

}

