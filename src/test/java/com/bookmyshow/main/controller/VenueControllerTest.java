//package com.bookmyshow.main.controller;
//import static org.hamcrest.Matchers.hasSize;
//
//import com.bookmyshow.main.dto.VenueDto;
//import com.bookmyshow.main.service.VenueService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false) 
//public class VenueControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private VenueService venueService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void testCreateVenue() throws Exception {
//        VenueDto venueDto = new VenueDto("PVR Cinemas", "Mall Road", "Delhi");
//
//        Mockito.when(venueService.createVenue(any(VenueDto.class))).thenReturn(venueDto);
//
//        mockMvc.perform(post("/venue/createVenue")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(venueDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("PVR Cinemas"))
//                .andExpect(jsonPath("$.location").value("Mall Road"))
//                .andExpect(jsonPath("$.city").value("Delhi"));
//    }
//
//    @Test
//    void testGetAllVenues() throws Exception {
//        List<VenueDto> venues = Arrays.asList(
//                new VenueDto("INOX", "Main Street", "Mumbai"),
//                new VenueDto("Cinepolis", "Sector 18", "Noida")
//        );
//
//        Mockito.when(venueService.getAllVenues()).thenReturn(venues);
//
//        mockMvc.perform(get("/venue/getAll"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(2))
//                .andExpect(jsonPath("$[0].name").value("INOX"))
//                .andExpect(jsonPath("$[1].city").value("Noida"));
//    }
//    
//    @Test
//    void getVenueByName_shouldReturnList_whenFound() throws Exception {
//        List<VenueDto> venues = Arrays.asList(
//            new VenueDto("PVR Cinema", "Gaur city", "Noida"),
//            new VenueDto("PVR Cinema", "Uptown", "Delhi")
//        );
// 
//        Mockito.when(venueService.getVenuesByName("Arena")).thenReturn(venues);
// 
//        mockMvc.perform(get("/venue/getByName")   
//                .param("name", "PVR Cinema"))
////            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$", hasSize(2)))
//            .andExpect(jsonPath("$[0].name").value("PVR Cinema"))
//            .andExpect(jsonPath("$[0].location").value("Gaur city"))
//            .andExpect(jsonPath("$[0].city").value("Noida"))
//            .andExpect(jsonPath("$[1].name").value("PVR Cinema"))
//            .andExpect(jsonPath("$[1].city").value("Delhi"));
//    }
// 
//
//    @Test
//    void getVenueByName_shouldReturn404_whenNotFound() throws Exception {
//        Mockito.when(venueService.getVenuesByName("Unknown")).thenReturn(Collections.emptyList());
//
//        mockMvc.perform(get("/venue/getByName")   
//                .param("name", "Unknown"))
//            .andExpect(status().isNotFound());
//    }
//
//
//       @Test
//       void softDeleteVenue_shouldReturn200_whenDeleted() throws Exception {
//        Mockito.when(venueService.softDeleteVenue(1L)).thenReturn(true);
//
//        mockMvc.perform(patch("/delete/1"));
//    }
//
//      @Test
//      void softDeleteVenue_shouldReturn404_whenNotFound() throws Exception {
//        Mockito.when(venueService.softDeleteVenue(99L)).thenReturn(false);
//
//        mockMvc.perform(patch("/delete/99"));
////            .andExpect(status().isNotFound());
//    }
//}
