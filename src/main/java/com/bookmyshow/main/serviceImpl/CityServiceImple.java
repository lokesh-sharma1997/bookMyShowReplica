package com.bookmyshow.main.serviceImpl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.main.dto.CityDTO;
import com.bookmyshow.main.model.City;
import com.bookmyshow.main.repository.CityRepository;
import com.bookmyshow.main.response.CityResponseDto;
import com.bookmyshow.main.service.CityService;

@Service
public class CityServiceImple implements CityService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CityRepository cityRepository;  

  
    private CityDTO CityToDto(City city) {
        return mapper.map(city, CityDTO.class);
    }

    private City DtoToCity(CityDTO cityDto) {
        return mapper.map(cityDto, City.class);
    }

 
    private CityResponseDto cityToCityResponseDto(City city) {
        return mapper.map(city, CityResponseDto.class);
    }

    public List<CityResponseDto> getAllCities() {
        return cityRepository.findAll().stream()
                .map(this::cityToCityResponseDto)
                .sorted(Comparator.comparing(CityResponseDto::getCityName))
                .collect(Collectors.toList());
    }

    public List<CityDTO> getPopularCities() {
        return cityRepository.findAll().stream()
                .filter(City::getPopular)
                .map(this::CityToDto)
                .collect(Collectors.toList());
    }
}
