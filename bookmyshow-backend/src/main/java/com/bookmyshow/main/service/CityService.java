package com.bookmyshow.main.service;


import java.util.List;

import com.bookmyshow.main.model.City;

public interface CityService {

    List<City> getAllCities();

    List<City> getPopularCities();
}

