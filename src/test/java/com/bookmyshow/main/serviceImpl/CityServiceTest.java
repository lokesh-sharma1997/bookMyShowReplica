package com.bookmyshow.main.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.bookmyshow.main.dto.CityDTO;
import com.bookmyshow.main.model.City;
import com.bookmyshow.main.repository.CityRepository;
import com.bookmyshow.main.response.CityResponseDto;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private CityServiceImple cityService;


    @Test
    void testGetAllCities() throws Exception{
      
        City city1 = new City();
        city1.setName("Delhi");
       

        City city2 = new City();
        city2.setName("Mumbai");
           

        List<City> cities = List.of(city1, city2);

        when(cityRepository.findAll()).thenReturn(cities);

        CityResponseDto dto1 = new CityResponseDto();
        dto1.setCityName("Delhi");
        
        
        CityResponseDto dto2 = new CityResponseDto();
        dto2.setCityName("Mumbai");
       
        when(mapper.map(city1, CityResponseDto.class)).thenReturn(dto1);
        when(mapper.map(city2, CityResponseDto.class)).thenReturn(dto2);

        
        List<CityResponseDto> result = cityService.getAllCities();

       
        assertEquals(2, result.size());
        assertEquals("Delhi", result.get(0).getCityName());
        assertEquals("Mumbai", result.get(1).getCityName());
    }

    @Test
    void testGetPopularCities() {
     
        City city1 = new City();
        city1.setName("Delhi");
        city1.setPopular(false);

        City city2 = new City();
        city2.setName("Mumbai");
        city2.setPopular(true);

        City city3 = new City();
        city3.setName("Chennai");
        city3.setPopular(true);

        List<City> cities = List.of(city1, city2, city3);

        when(cityRepository.findAll()).thenReturn(cities);
        CityDTO dto1 = new CityDTO();
        dto1.setCityName("Delhi");
        dto1 .setPopularCity(false);
        
        CityDTO dto2 = new CityDTO();
        dto2.setCityName("Mumbai");
        dto2 .setPopularCity(false);
        
        when(mapper.map(city2, CityDTO.class)).thenReturn(dto1);
        when(mapper.map(city3, CityDTO.class)).thenReturn(dto2);

      
        List<CityDTO> result = cityService.getPopularCities();

      
        assertEquals(2, result.size());

        assertEquals("Delhi", result.get(0).getCityName());
        assertEquals("Mumbai", result.get(1).getCityName());
    }
}
