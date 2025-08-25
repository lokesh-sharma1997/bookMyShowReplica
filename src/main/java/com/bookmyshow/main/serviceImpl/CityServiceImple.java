package com.bookmyshow.main.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.CityDto;
import com.bookmyshow.main.model.City;
import com.bookmyshow.main.repository.CityRepository;
import com.bookmyshow.main.service.CityService;

@Service
public class CityServiceImple implements CityService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CityRepository cityRepository;  

  
    private CityDto CityToDto(City city) {
        return mapper.map(city, CityDto.class);
    }

    private City DtoToCity(CityDto cityDto) {
        return mapper.map(cityDto, City.class);
    }

  
	/*
	 * @PostConstruct public void loadCitiesFromCsv() { try (BufferedReader br = new
	 * BufferedReader(new InputStreamReader(
	 * getClass().getResourceAsStream("/cities.csv")))) {
	 * 
	 * List<City> cities = br.lines().skip(1).map(line -> { String[] data =
	 * line.split(","); String name = data[0].trim(); Boolean popular =
	 * Boolean.parseBoolean(data[1].trim()); String imageUrl = data.length > 2 ?
	 * data[2].trim() : "";
	 * 
	 * 
	 * return new City(null, name, popular, imageUrl);
	 * }).collect(Collectors.toList());
	 * 
	 * 
	 * cityRepository.saveAll(cities);
	 * 
	 * System.out.println("Cities saved into DB: " + cities.size());
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */

  
    public List<CityDto> getAllCities() {
        return cityRepository.findAll().stream()
                .filter(city -> !city.getPopular())
                .map(this::CityToDto)
                .collect(Collectors.toList());
    }

    
    public List<CityDto> getPopularCities() {
        return cityRepository.findAll().stream()
                .filter(City::getPopular)
                .map(this::CityToDto)
                .collect(Collectors.toList());
    }
}
