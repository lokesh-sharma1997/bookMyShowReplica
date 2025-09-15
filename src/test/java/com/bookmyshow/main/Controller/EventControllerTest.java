package com.bookmyshow.main.Controller;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.bookmyshow.main.controller.EventController;
import com.bookmyshow.main.dto.CategoryDTO;
import com.bookmyshow.main.dto.DateFilterDTO;
import com.bookmyshow.main.dto.EventDTO;
import com.bookmyshow.main.dto.EventFilterRequest;
import com.bookmyshow.main.dto.EventResponseDto;
import com.bookmyshow.main.dto.EventResponseDtoCard;
import com.bookmyshow.main.dto.EventSearchRequestDto;
import com.bookmyshow.main.dto.FormatDTO;
import com.bookmyshow.main.dto.GenresDTO;
import com.bookmyshow.main.dto.LanguagesDTO;
import com.bookmyshow.main.dto.MoreFilterDTO;
import com.bookmyshow.main.dto.PriceDTO;
import com.bookmyshow.main.dto.ReleaseMonthDTO;
import com.bookmyshow.main.dto.TagDTO;
import com.bookmyshow.main.exception.EventCustomException;
import com.bookmyshow.main.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.eq;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

   
    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    private EventDTO eventDto;

    
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
    	objectMapper = new ObjectMapper();
    	objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();

        eventDto = new EventDTO();
        eventDto.setEventId(1L);
        eventDto.setName("Test Event");
        eventDto.setDescription("Description");
        eventDto.setRunTime("120");
        eventDto.setStartDate(LocalDate.of(2025, 9, 8));
        eventDto.setEndDate(LocalDate.of(2025, 9, 10));
        eventDto.setEventType("Movie");
        eventDto.setImageurl("imageUrl");
        eventDto.setImdbRating(8.5);
        eventDto.setLikes(100.0);
        eventDto.setVotes(50.0);
        eventDto.setCurrentlyPlaying(true);
        eventDto.setDeleted(false);
        eventDto.setAgeLimit(13);
        eventDto.setReleasingOn(LocalDate.of(2025, 9, 8));
        eventDto.setLanguages(Collections.emptyList());
        eventDto.setGenres(Collections.emptyList());
        eventDto.setFormat(Collections.emptyList());
        eventDto.setTag(Collections.emptyList());
        eventDto.setReleaseMonth(Collections.emptyList());
        eventDto.setDateFilter(Collections.emptyList());
        eventDto.setCategories(Collections.emptyList());
        eventDto.setMoreFilters(Collections.emptyList());
        eventDto.setPrice(Collections.emptyList());
        eventDto.setCast(Collections.emptyList());
        eventDto.setCrew(Collections.emptyList());
        eventDto.setCity(Collections.emptyList());
    }

    @Test
    void testCreateEvent() throws Exception {
        when(eventService.createEvent(any(EventDTO.class), any(), any(), any()))
                .thenReturn(eventDto);

        MockMultipartFile eventJson = new MockMultipartFile(
                "event",
                "",
                "application/json",
                objectMapper.writeValueAsBytes(eventDto)
        );

        MockMultipartFile poster = new MockMultipartFile(
                "poster",
                "poster.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "fake-image".getBytes()
        );

        mockMvc.perform(multipart("/api/events/create-event")
                .file(eventJson)
                .file(poster)
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.message").value("Event created successfully"))
        .andExpect(jsonPath("$.data").doesNotExist()); 
    }

    @Test
    void testGetEventById_Success() throws Exception {
       
        EventResponseDto mockEvent = new EventResponseDto();
        mockEvent.setEventId(1L);
        mockEvent.setName("Test Event");

        when(eventService.getEventById(1L)).thenReturn(mockEvent);
       
        mockMvc.perform(get("/api/events/1"))
            .andExpect(status().isCreated()) 
            .andExpect(jsonPath("$.statusCode").value(201))
            .andExpect(jsonPath("$.message").value("Event fetched successfully"))
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.name").value("Test Event"))
            .andExpect(jsonPath("$.data.eventId").value(1));
    }
    



    @Test
    void testFilterEvents() throws Exception {
       
        EventFilterRequest filterRequest = new EventFilterRequest();
        filterRequest.setType("Movie");
        filterRequest.setLanguages(Arrays.asList(1, 2));
        filterRequest.setGenres(Arrays.asList(3, 4));
        filterRequest.setFormats(Arrays.asList(5, 6));
        filterRequest.setTags(Arrays.asList(7, 8));
        filterRequest.setCategories(Arrays.asList(9, 10));
        filterRequest.setPrice(Arrays.asList(100, 200));
        filterRequest.setMorefilter(Arrays.asList(11, 12));
        filterRequest.setReleaseMonths(Arrays.asList(1, 2));
        filterRequest.setDateFilters(Arrays.asList(13, 14));

        
        EventResponseDtoCard event = new EventResponseDtoCard();
        event.setEventId(1L);
        event.setName("Sample Movie");

        List<EventResponseDtoCard> mockResponse = Arrays.asList(event);

       
        when(eventService.filterEvents(
                eq("Movie"),
                eq(Arrays.asList(1, 2)),
                eq(Arrays.asList(3, 4)),
                eq(Arrays.asList(5, 6)),
                eq(Arrays.asList(7, 8)),
                eq(Arrays.asList(9, 10)),
                eq(Arrays.asList(100, 200)),
                eq(Arrays.asList(11, 12)),
                eq(Arrays.asList(1, 2)),
                eq(Arrays.asList(13, 14))
        )).thenReturn(mockResponse);

       
        mockMvc.perform(post("/api/events/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filterRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.statusCode").value(200))
            .andExpect(jsonPath("$.message").value("Events filtered successfully"))
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data[0].eventId").value(1))
            .andExpect(jsonPath("$.data[0].name").value("Sample Movie")); 
    }

    @Test
    void testDeleteEvent() throws Exception {
        Long eventId = 1L;

        
        when(eventService.deleteEvent(eventId)).thenReturn(true);

        mockMvc.perform(patch("/api/events/delete/{id}", eventId))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.statusCode").value(201))
            .andExpect(jsonPath("$.message").value("Event deleted successfully"))
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data").doesNotExist()); 
    }
    @Test
    void testGetPopularEvents() throws Exception {
        EventResponseDtoCard event1 = new EventResponseDtoCard();
        event1.setEventId(1L);
        event1.setName("Popular Event 1");

        EventResponseDtoCard event2 = new EventResponseDtoCard();
        event2.setEventId(2L);
        event2.setName("Popular Event 2");

        List<EventResponseDtoCard> popularEvents = Arrays.asList(event1, event2);

        when(eventService.getPopularEvents("Movie")).thenReturn(popularEvents);

        mockMvc.perform(get("/api/events/get-popular-events")
                .param("eventType", "Movie"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.statusCode").value(201))
            .andExpect(jsonPath("$.message").value("Popolar Events fetch  successfully"))
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data[0].eventId").value(1))
            .andExpect(jsonPath("$.data[0].name").value("Popular Event 1"))
            .andExpect(jsonPath("$.data[1].eventId").value(2))
            .andExpect(jsonPath("$.data[1].name").value("Popular Event 2"));
    }


    @Test
    void testSearchEventNames() throws Exception {
       
        List<String> mockEventNames = Arrays.asList("Concert", "Conference");
        EventSearchRequestDto requestDto = new EventSearchRequestDto();
        requestDto.setName("Con");
        requestDto.setEventTypes(Arrays.asList("Music", "Business"));

        
        when(eventService.searchEventNames(requestDto.getName(), requestDto.getEventTypes()))
                .thenReturn(mockEventNames);

      
        mockMvc.perform(post("/api/events/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(200))
           .andExpect(jsonPath("$.message").value("Event names fetched successfully"))
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data[0]").value("Concert"))
            .andExpect(jsonPath("$.data[1]").value("Conference"));
    }

   


//    
//    @Test
//    void testGetLanguages() throws Exception {
//        // Sample mock data
//        LanguagesDTO lang1 = new LanguagesDTO();
//        lang1.setLanguageId(1L);
//        lang1.setLanguageName("English");
//
//        LanguagesDTO lang2 = new LanguagesDTO();
//        lang2.setLanguageId(2L);
//        lang2.setLanguageName("Hindi");
//
//        List<LanguagesDTO> languages = Arrays.asList(lang1, lang2);
//
//        // Mock the service response
//        when(eventService.getAllLanguages()).thenReturn(languages);
//
//        mockMvc.perform(get("/api/events/languages"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.statusCode").value(200))
//            .andExpect(jsonPath("$.message").value("Fetch all languages successfully"))
//            .andExpect(jsonPath("$.success").value(true))
//            .andExpect(jsonPath("$.data").isArray())
//            .andExpect(jsonPath("$.data.length()").value(2))
//            .andExpect(jsonPath("$.data[0].languageName").value("English"))
//            .andExpect(jsonPath("$.data[1].languageName").value("Hindi"));
//    }

//    @Test
//    void testGetGenres() throws Exception {
//        GenresDTO genre1 = new GenresDTO();
//        genre1.setGenresId(1L);
//        genre1.setGenresName("Action");
//
//        GenresDTO genre2 = new GenresDTO();
//        genre2.setGenresId(2L);
//        genre2.setGenresName("Comedy");
//
//        when(eventService.getAllGenres()).thenReturn(Arrays.asList(genre1, genre2));
//
//        mockMvc.perform(get("/api/events/genres"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.statusCode").value(200))
//            .andExpect(jsonPath("$.message").value("Fetch all Genres successfully"))
//            .andExpect(jsonPath("$.success").value(true))
//            .andExpect(jsonPath("$.data[0].genresName").value("Action"))
//            .andExpect(jsonPath("$.data[1].genresName").value("Comedy"));
//    }

    @Test
    void testGetFormats() throws Exception {
        FormatDTO format1 = new FormatDTO();
        format1.setFormatId(1L);
        format1.setFormatName("2D");

        FormatDTO format2 = new FormatDTO();
        format2.setFormatId(2L);
        format2.setFormatName("IMAX");

        when(eventService.getAllFormats()).thenReturn(Arrays.asList(format1, format2));

        mockMvc.perform(get("/api/events/formats"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Fetch all Formats successfully"))
            .andExpect(jsonPath("$.data[0].formatName").value("2D"))
            .andExpect(jsonPath("$.data[1].formatName").value("IMAX"));
    }

    @Test
    void testGetTags() throws Exception {
        TagDTO tag1 = new TagDTO();
        tag1.setTagId(1L);
        tag1.setTagName("Trending");

        TagDTO tag2 = new TagDTO();
        tag2.setTagId(2L);
        tag2.setTagName("New Release");

        when(eventService.getAllTags()).thenReturn(Arrays.asList(tag1, tag2));

        mockMvc.perform(get("/api/events/tags"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Fetch all Tags successfully"))
            .andExpect(jsonPath("$.data[0].tagName").value("Trending"))
            .andExpect(jsonPath("$.data[1].tagName").value("New Release"));
    }
    
    @Test
    void testGetReleaseMonths() throws Exception {
        ReleaseMonthDTO month1 = new ReleaseMonthDTO();
        month1.setReleaseMonthId(1L);
        month1.setReleaseMonthName("January");

        when(eventService.getAllReleaseMonths()).thenReturn(List.of(month1));

        mockMvc.perform(get("/api/events/release-months"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Fetch all Release Months successfully"))
            .andExpect(jsonPath("$.data[0].releaseMonthName").value("January"));
    }

    
    @Test
    void testGetDateFilters() throws Exception {
        DateFilterDTO filter = new DateFilterDTO();
        filter.setDateFilterId(1L);
        filter.setDateFilterName("This Weekend");

        when(eventService.getAllDateFilters()).thenReturn(List.of(filter));

        mockMvc.perform(get("/api/events/date-filters"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Fetch all Date Filters successfully"))
            .andExpect(jsonPath("$.data[0].dateFilterName").value("This Weekend"));
    }

//    @Test
//    void testGetCategories() throws Exception {
//        CategoryDTO category = new CategoryDTO();
//        category.setCategoryId(1L);
//        category.setCategoryName("Family");
//
//        when(eventService.getAllCategories()).thenReturn(List.of(category));
//
//        mockMvc.perform(get("/api/events/categories"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.message").value("Fetch all Categories successfully"))
//            .andExpect(jsonPath("$.data[0].categoryName").value("Family"));
//    }
//    @Test
//    void testGetMoreFilters() throws Exception {
//        MoreFilterDTO filter = new MoreFilterDTO();
//        filter.setMoreFilterId(1L);
//        filter.setMoreFilterName("Subtitled");
//
//        when(eventService.getAllMoreFilters()).thenReturn(List.of(filter));
//
//        mockMvc.perform(get("/api/events/more-filters"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.message").value("Fetch all More Filters successfully"))
//            .andExpect(jsonPath("$.data[0].moreFilterName").value("Subtitled"));
//    }
    
    @Test
    void testGetPrices() throws Exception {
        PriceDTO price = new PriceDTO();
        price.setPriceId(1L);
        price.setPriceRange("₹100 - ₹200");

        when(eventService.getAllPrices()).thenReturn(List.of(price));

        mockMvc.perform(get("/api/events/prices"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Fetch all Prices successfully"))
            .andExpect(jsonPath("$.data[0].priceRange").value("₹100 - ₹200"));
    }

}
