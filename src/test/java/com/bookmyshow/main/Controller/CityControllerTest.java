package com.bookmyshow.main.Controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bookmyshow.main.controller.CityController;
import com.bookmyshow.main.dto.CityDTO;
import com.bookmyshow.main.service.CityService;

@ExtendWith(MockitoExtension.class)
public class CityControllerTest {
	 private MockMvc mockMvc;
	
	@Mock
	private CityService cityService;
	
    @InjectMocks
    private CityController cityController;
    
    @BeforeEach
    void setUp() {
    	 mockMvc = MockMvcBuilders.standaloneSetup(cityController).build();
    }
    
    @Test
    void testGetAllCities()throws Exception
    {
    	CityDTO cities = new CityDTO();
    	cities.setCityName("Agra");
    	when(cityService.getAllCities()).thenReturn(List.of(cities));
    	mockMvc.perform(get("/api/city/all"))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$.statusCode").value(200))
        .andExpect(jsonPath("$.message").value("Fetch All Cities successfully"));
        
    }
    
    
    @Test 
    void testGetPopularCities()throws Exception
    {
    	CityDTO cities = new CityDTO();
    	cities.setCityName("Agra");
    	when(cityService.getPopularCities()).thenReturn(List.of(cities));
    	mockMvc.perform(get("/api/city/popular"))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$.statusCode").value(200))
        .andExpect(jsonPath("$.message").value("Fetch Popular Cities successfully"));
    }

}
