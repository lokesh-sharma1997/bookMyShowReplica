package com.bookmyshow.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookmyshow.main.model.City;

public interface CityRepository extends JpaRepository<City, Integer> {

	City findByName(String cityName);

}
