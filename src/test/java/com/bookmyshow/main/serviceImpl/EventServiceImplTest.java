//package com.bookmyshow.main.serviceImpl;
//
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyList;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.lenient;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.bookmyshow.main.dto.CategoryDTO;
//import com.bookmyshow.main.dto.DateFilterDTO;
//import com.bookmyshow.main.dto.EventDTO;
//import com.bookmyshow.main.dto.EventResponseDtoCard;
//import com.bookmyshow.main.dto.FormatDTO;
//import com.bookmyshow.main.dto.GenresDTO;
//import com.bookmyshow.main.dto.LanguagesDTO;
//import com.bookmyshow.main.dto.MoreFilterDTO;
//import com.bookmyshow.main.dto.PriceDTO;
//import com.bookmyshow.main.dto.ReleaseMonthDTO;
//import com.bookmyshow.main.dto.TagDTO;
//import com.bookmyshow.main.exception.EventCustomException;
//import com.bookmyshow.main.model.Categories;
//import com.bookmyshow.main.model.DateFilter;
//import com.bookmyshow.main.model.Event;
//import com.bookmyshow.main.model.Format;
//import com.bookmyshow.main.model.Genres;
//import com.bookmyshow.main.model.Languages;
//import com.bookmyshow.main.model.MoreFilters;
//import com.bookmyshow.main.model.Price;
//import com.bookmyshow.main.model.ReleaseMonth;
//import com.bookmyshow.main.model.Tag;
//import com.bookmyshow.main.repository.CastRepository;
//import com.bookmyshow.main.repository.CategoriesRepository;
//import com.bookmyshow.main.repository.CityRepository;
//import com.bookmyshow.main.repository.CrewRepository;
//import com.bookmyshow.main.repository.DateFilterRepository;
//import com.bookmyshow.main.repository.EventRepository;
//import com.bookmyshow.main.repository.FormatRepository;
//import com.bookmyshow.main.repository.GenresRepository;
//import com.bookmyshow.main.repository.LanguagesRepository;
//import com.bookmyshow.main.repository.MoreFiltersRepository;
//import com.bookmyshow.main.repository.PriceRepository;
//import com.bookmyshow.main.repository.ReleaseMonthRepository;
//import com.bookmyshow.main.specification.EventSpecification;
//
//@ExtendWith(MockitoExtension.class)
//class EventServiceImplTest {
//
//    @Mock
//    private EventRepository eventRepository;
//    @Mock
//    private LanguagesRepository languagesRepository;
//    @Mock
//    private GenresRepository genresRepository;
//    @Mock
//    private FormatRepository formatRepository;
//    @Mock
//    private TagRepository tagRepository;
//    @Mock
//    private ReleaseMonthRepository releaseMonthRepository;
//    @Mock
//    private DateFilterRepository dateFilterRepository;
//    @Mock
//    private CategoriesRepository categoriesRepository;
//    @Mock
//    private MoreFiltersRepository moreFiltersRepository;
//    @Mock
//    private PriceRepository priceRepository;
//    @Mock
//    private CastRepository castRepository;
//    @Mock
//    private CrewRepository crewRepository;
//    @Mock
//    private CityRepository cityRepository;
//
//    @Mock
//    private ModelMapper mapper;
//
//    @InjectMocks
//    private EventServiceImpl eventService;
//
//    private Event event;
//    private EventDTO eventDto;
//
//    @BeforeEach
//    void setUp() {
//        event = new Event();
//        event.setEventId(1L);
//        event.setName("Test Event");
//        event.setDescription("Desc");
//        event.setRunTime("120min");
//        event.setStartDate(LocalDate.of(2025, 9, 8));
//        event.setEndDate(LocalDate.of(2025, 9, 10));
//        event.setEventType("Movie");
//        event.setImageurl("imageUrl");
//        event.setImdbRating(8.5);
//        event.setLikes(100.0);
//        event.setVotes(50.0);
//        event.setCurrentlyPlaying(true);
//        event.setDeleted(false);
//        event.setAgeLimit(13);
//        event.setReleasingOn(LocalDate.of(2025, 9, 8));
//        event.setLanguages(new ArrayList<>());
//        event.setGenres(new ArrayList<>());
//        event.setFormat(new ArrayList<>());
//        event.setTag(new ArrayList<>());
//        event.setReleaseMonth(new ArrayList<>());
//        event.setDateFilter(new ArrayList<>());
//        event.setCategories(new ArrayList<>());
//        event.setMoreFilters(new ArrayList<>());
//        event.setPrice(new ArrayList<>());
//        event.setCast(new ArrayList<>());
//        event.setCrew(new ArrayList<>());
//        event.setCity(new ArrayList<>());
//
//        eventDto = new EventDTO();
//        eventDto.setEventId(1L);
//        eventDto.setName("Test Event");
//        eventDto.setDescription("Desc");
//        eventDto.setRunTime("120");
//        eventDto.setStartDate(LocalDate.of(2025, 9, 8));
//        eventDto.setEndDate(LocalDate.of(2025, 9, 10));
//        eventDto.setEventType("Movie");
//        eventDto.setImageurl("imageUrl");
//        eventDto.setImdbRating(8.5);
//        eventDto.setLikes(100.0);
//        eventDto.setVotes(50.0);
//        eventDto.setCurrentlyPlaying(true);
//        eventDto.setDeleted(false);
//        eventDto.setAgeLimit(13);
//        eventDto.setReleasingOn(LocalDate.of(2025, 9, 8));
//        eventDto.setLanguages(Collections.emptyList());
//        eventDto.setGenres(Collections.emptyList());
//        eventDto.setFormat(Collections.emptyList());
//        eventDto.setTag(Collections.emptyList());
//        eventDto.setReleaseMonth(Collections.emptyList());
//        eventDto.setDateFilter(Collections.emptyList());
//        eventDto.setCategories(Collections.emptyList());
//        eventDto.setMoreFilters(Collections.emptyList());
//        eventDto.setPrice(Collections.emptyList());
//        eventDto.setCast(Collections.emptyList());
//        eventDto.setCrew(Collections.emptyList());
//        eventDto.setCity(Collections.emptyList());
//    }
//
//    @Test
//    void testCreateEvent() throws IOException {
//        MockMultipartFile poster = new MockMultipartFile("poster", "poster.jpg", "image/jpeg", "dummy image".getBytes());
//
//        when(mapper.map(any(EventDTO.class), eq(Event.class))).thenReturn(event);
//        when(languagesRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
//        when(genresRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
//        when(formatRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
//        when(tagRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
//        when(releaseMonthRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
//        when(dateFilterRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
//        when(categoriesRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
//        when(moreFiltersRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
//        lenient().when(castRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
//
//        when(cityRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
//        when(eventRepository.save(any(Event.class))).thenReturn(event);
//
//        EventDTO result = eventService.createEvent(eventDto, poster, null, null);
//
//        assertNotNull(result);
//        assertEquals(event.getName(), result.getName());
//        verify(eventRepository).save(any(Event.class));
//    }
//
////    @Test
////    void testGetEventByIdFound() {
////        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
////
////
////        EventDTO result = eventService.getEventById(1L);
////
////        assertNotNull(result);
////        assertEquals("Test Event", result.getName());
////    }
//
//    @Test
//    void testGetEventByIdNotFound() {
//        when(eventRepository.findById(1L)).thenReturn(Optional.empty());
//
//        Exception ex = assertThrows(EventCustomException.class, () -> eventService.getEventById(1L));
//        assertEquals("Event not found with id: 1", ex.getMessage());
//    }
//
//    @Test
//    void testGetAllLanguages() {
//        Languages lang = new Languages();
//        lang.setLanguageId(1L);
//        when(languagesRepository.findAll()).thenReturn(List.of(lang));
//        LanguagesDTO langDto = new LanguagesDTO();
//        when(mapper.map(lang, LanguagesDTO.class)).thenReturn(langDto);
//
//        List<LanguagesDTO> result = eventService.getAllLanguages();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(languagesRepository).findAll();
//    }
//
//    @Test
//    void testGetAllGenres() {
//        Genres genre = new Genres();
//        genre.setGenreId(1L);
//        when(genresRepository.findAll()).thenReturn(List.of(genre));
//        GenresDTO genreDto = new GenresDTO();
//        when(mapper.map(genre, GenresDTO.class)).thenReturn(genreDto);
//
//        List<GenresDTO> result = eventService.getAllGenres();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(genresRepository).findAll();
//    }
//
//    @Test
//    void testGetAllFormats() {
//        Format format = new Format();
//        format.setFormatId(1L);
//        when(formatRepository.findAll()).thenReturn(List.of(format));
//        FormatDTO formatDto = new FormatDTO();
//        when(mapper.map(format, FormatDTO.class)).thenReturn(formatDto);
//
//        List<FormatDTO> result = eventService.getAllFormats();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(formatRepository).findAll();
//    }
//
//    @Test
//    void testGetAllTags() {
//        Tag tag = new Tag();
//        tag.setTagId(1L);
//        when(tagRepository.findAll()).thenReturn(List.of(tag));
//        TagDTO tagDto = new TagDTO();
//        when(mapper.map(tag, TagDTO.class)).thenReturn(tagDto);
//
//        List<TagDTO> result = eventService.getAllTags();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(tagRepository).findAll();
//    }
//
//    @Test
//    void testGetAllReleaseMonths() {
//        ReleaseMonth month = new ReleaseMonth();
//        month.setReleaseMonthId(1L);
//        when(releaseMonthRepository.findAll()).thenReturn(List.of(month));
//        ReleaseMonthDTO monthDto = new ReleaseMonthDTO();
//        when(mapper.map(month, ReleaseMonthDTO.class)).thenReturn(monthDto);
//
//        List<ReleaseMonthDTO> result = eventService.getAllReleaseMonths();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(releaseMonthRepository).findAll();
//    }
//
//    @Test
//    void testGetAllDateFilters() {
//        DateFilter filter = new DateFilter();
//        filter.setDateFilterId(1L);
//        when(dateFilterRepository.findAll()).thenReturn(List.of(filter));
//        DateFilterDTO filterDto = new DateFilterDTO();
//        when(mapper.map(filter, DateFilterDTO.class)).thenReturn(filterDto);
//
//        List<DateFilterDTO> result = eventService.getAllDateFilters();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(dateFilterRepository).findAll();
//    }
//
//    @Test
//    void testGetAllCategories() {
//        Categories cat = new Categories();
//        cat.setCategoryId(1L);
//        cat.setCategoriesName("Category1");
//        when(categoriesRepository.findAll()).thenReturn(List.of(cat));
//
//        List<CategoryDTO> result = eventService.getAllCategories();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(1L, result.get(0).getCategoryId());
//        assertEquals("Category1", result.get(0).getCategoryName());
//        verify(categoriesRepository).findAll();
//    }
//
//    @Test
//    void testGetAllMoreFilters() {
//        MoreFilters filter = new MoreFilters();
//        filter.setFilterId(1L);
//        when(moreFiltersRepository.findAll()).thenReturn(List.of(filter));
//        MoreFilterDTO filterDto = new MoreFilterDTO();
//   
//
//        List<MoreFilterDTO> result = eventService.getAllMoreFilters();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(moreFiltersRepository).findAll();
//    }
//
//    @Test
//    void testGetAllPrice() {
//        Price price = new Price();
//        price.setPriceId(1L);
//        when(priceRepository.findAll()).thenReturn(List.of(price));
//        PriceDTO priceDto = new PriceDTO();
//        when(mapper.map(price, PriceDTO.class)).thenReturn(priceDto);
//
//        List<PriceDTO> result = eventService.getAllPrices();
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        verify(priceRepository).findAll();
//    }
//    @Test
//    void testfilter() {
//        String type = "Movie";
//        List<Integer> languages = List.of(1, 2);
//        List<Integer> genres = List.of(1, 2);
//        List<Integer> formats = List.of(1, 2);
//        List<Integer> tags = List.of(1, 2);
//        List<Integer> categories = List.of(1, 2);
//        List<Integer> price = List.of(1, 2);
//        List<Integer> moreFilters = List.of(1, 2);
//        List<Integer> releaseMonths = List.of(1, 2);
//        List<Integer> dateFilters = List.of(1, 2);
//
//        Event event1 = new Event();
//        event1.setDeleted(false); 
//        Event event2 = new Event();
//        event2.setDeleted(true);  
//
//        List<Event> events = List.of(event1, event2);
//        Specification<Event> mockSpec = Mockito.mock(Specification.class);
//
//        try (MockedStatic<EventSpecification> mockedStatic = Mockito.mockStatic(EventSpecification.class)) {
//            mockedStatic.when(() -> EventSpecification.filterEvents(
//                type, languages, genres, formats, tags, categories, price, moreFilters, releaseMonths, dateFilters
//            )).thenReturn(mockSpec);
//
//            when(eventRepository.findAll(mockSpec)).thenReturn(events);
//
//           
//            EventResponseDtoCard dto = new EventResponseDtoCard();
//            EventServiceImpl spyService = Mockito.spy(eventService);
//         
//
//            List<EventResponseDtoCard> result = spyService.filterEvents(
//                type, languages, genres, formats, tags, categories, price, moreFilters, releaseMonths, dateFilters);
//
//            assertEquals(1, result.size());
//        
//
//            
//            verify(eventRepository).findAll(mockSpec);
//        }
//    }
//    @Test
//    void testSearchEventNames_WithEventTypes_ReturnsMatchingEvents() {
//        String name = "Tech";
//        List<String> eventTypes = List.of("Conference", "Webinar");
//
//        List<Event> mockEvents = List.of(
//            new Event(),
//            new Event()
//        );
//
//        when(eventRepository.searchByNameAndEventTypes(eq(name), anyList()))
//            .thenReturn(mockEvents);
//
//        List<String> result = eventService.searchEventNames(name, eventTypes);
//
//       
//    }
//
////    @Test
////    void testSearchEventNames_WithoutEventTypes_EventFound() {
////        String name = "Hackathon";
////        Event event = new Event();
////
////        when(eventRepository.searchByNameOnly(name)).thenReturn(Optional.of(event));
////
////        List<String> result = eventService.searchEventNames(name, null);
////
////        
////    }
////
////    @Test
////    void testSearchEventNames_WithoutEventTypes_EventNotFound_ThrowsException() {
////        String name = "Nonexistent";
////
////        when(eventRepository.findByName(name)).thenReturn(Optional.empty());
////
////        assertThrows(EventCustomException.class, () -> {
////            eventService.searchEventNames(name, null);
////        });
////    }
////    
//    @Test
//    void testGetPopularEvents_WithEventType_ReturnsFilteredEvents() {
//        String eventType = "Movie";
//
//        List<Event> mockEvents = List.of(
//          
//        );
//
//        when(eventRepository.findTop10ByEventTypeOrderByReleasingOnDesc(eventType))
//            .thenReturn(mockEvents);
//
//        List<EventResponseDtoCard> result = eventService.getPopularEvents(eventType);
//
//       
//    }
//    @Test
//    void testGetPopularEvents_WithoutEventType_ReturnsFilteredEvents() {
//        List<Event> mockEvents = List.of(
//            
//        );
//
//        when(eventRepository.findTop10ByOrderByReleasingOnDesc())
//            .thenReturn(mockEvents);
//
//        List<EventResponseDtoCard> result = eventService.getPopularEvents(null);
//
//        
//    }
//    @Test
//    void testgetEventById()
//    {
//    	Long eventidLong=1L;
//    	Event event = new Event();
//    	event.setEventId(eventidLong);
//    	  event.setDeleted(false);
//    	when(eventRepository.findById(eventidLong)).thenReturn(Optional.of(event));
//    	
//
//    	eventService.deleteEvent(eventidLong);
//    	verify(eventRepository).save(event);
//    }
//    void testUpdateEvent() throws IOException {
//        Long eventId = 1L;
//
//    
//        EventDTO eventDto = new EventDTO();
//        eventDto.setEventId(eventId);
//        eventDto.setName("Updated Event Name");
//        eventDto.setDescription("Updated Description");
//        eventDto.setRunTime("150");
//        eventDto.setEventType("Movie");
//
//      
//        MultipartFile poster = mock(MultipartFile.class);
//        when(poster.isEmpty()).thenReturn(false);
//        when(poster.getBytes()).thenReturn("dummyPosterBytes".getBytes());
//
//        
//        List<MultipartFile> castImages = List.of(mock(MultipartFile.class));
//
//        
//        Event existingEvent = new Event();
//        existingEvent.setEventId(eventId);
//        existingEvent.setName("Old Event Name");
//        existingEvent.setAgeLimit(0);
//      
//
//    
//        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
//        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//    
//        EventDTO result = eventService.updateEvent(eventId, eventDto, poster, castImages, List.of());
//
//       
//        verify(eventRepository).findById(eventId);
//        verify(eventRepository).save(any(Event.class));
//
//        
//        assertNotNull(result);
//        assertEquals("Updated Event Name", result.getName());
//        assertEquals("Updated Description", result.getDescription());
//        assertEquals("150", result.getRunTime());
//        assertEquals("Movie", result.getEventType());
//
//        
//        assertNotNull(result.getImageurl());
//    }
//
//
//
//}
