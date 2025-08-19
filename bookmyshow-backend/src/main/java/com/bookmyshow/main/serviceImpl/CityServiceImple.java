package com.bookmyshow.main.serviceImpl;



import com.bookmyshow.main.model.City;
import com.bookmyshow.main.service.CityService;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CityServiceImple implements CityService {

    private List<City> cities = new ArrayList<>();
    private AtomicLong idCounter = new AtomicLong(1);

    @PostConstruct
    public void loadCitiesFromCsv() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/cities.csv")))) {

            // Skip header
            cities = br.lines().skip(1).map(line -> {
                String[] data = line.split(",");
                Long id = idCounter.getAndIncrement();
                String name = data[0].trim();
                Boolean popular = Boolean.parseBoolean(data[1].trim());
                String imageUrl = data.length > 2 ? data[2].trim() : "";

                return new City(id, name, popular, imageUrl);
            }).collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<City> getAllCities() {
        return cities;
    }

    public List<City> getPopularCities() {
        return cities.stream().filter(City::getPopular).collect(Collectors.toList());
    }
}
