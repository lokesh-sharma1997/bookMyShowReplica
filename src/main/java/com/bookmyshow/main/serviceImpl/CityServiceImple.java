package com.bookmyshow.main.serviceImpl;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.CityDto;
import com.bookmyshow.main.model.City;
import com.bookmyshow.main.service.CityService;

import jakarta.annotation.PostConstruct;

@Service
public class CityServiceImple implements CityService {
	
	
	@Autowired
	public ModelMapper mapper;
	
	
	private  CityDto CityToDto(City city)
	{
		return mapper.map(city, CityDto.class);
	}
	
	private  City DtoToCity(CityDto cityDto)
	{
		return mapper.map(cityDto, City.class);
	}

    private List<City> cities = new ArrayList<>();
    private AtomicLong idCounter = new AtomicLong(1);

    @PostConstruct
    public void loadCitiesFromCsv() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/cities.csv")))) {
                  
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

    public List<CityDto> getAllCities() {
        return cities.stream()
                .filter(city -> !city.getPopular()) 
                .map(this::CityToDto)
                .collect(Collectors.toList());
    }

    
    public List<CityDto> getPopularCities() { 
        return cities.stream()
                .filter(City::getPopular)
                .map(this::CityToDto)
                .collect(Collectors.toList());
    }
   
   //Syso
    
}
