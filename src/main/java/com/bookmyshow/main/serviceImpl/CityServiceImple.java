package com.bookmyshow.main.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.CityDto;
import com.bookmyshow.main.model.City;
import com.bookmyshow.main.repository.CityRepository;
import com.bookmyshow.main.service.CityService;

import jakarta.annotation.PostConstruct;

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
