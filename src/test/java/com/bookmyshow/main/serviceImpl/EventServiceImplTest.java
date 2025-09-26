package com.bookmyshow.main.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import com.bookmyshow.main.dto.CastDTO;
import com.bookmyshow.main.dto.CategoryDTO;
import com.bookmyshow.main.dto.CrewDTO;
import com.bookmyshow.main.dto.DateFilterDTO;
import com.bookmyshow.main.dto.EventDTO;
import com.bookmyshow.main.dto.EventResponseDto;
import com.bookmyshow.main.dto.EventResponseDtoCard;
import com.bookmyshow.main.dto.EventSearchDTO;
import com.bookmyshow.main.dto.FormatDTO;
import com.bookmyshow.main.dto.GenresDTO;
import com.bookmyshow.main.dto.LanguagesDTO;
import com.bookmyshow.main.dto.MoreFilterDTO;
import com.bookmyshow.main.dto.PriceDTO;
import com.bookmyshow.main.dto.ReleaseMonthDTO;
import com.bookmyshow.main.dto.TagDTO;
import com.bookmyshow.main.exception.EventCustomException;
import com.bookmyshow.main.model.Cast;
import com.bookmyshow.main.model.Categories;
import com.bookmyshow.main.model.City;
import com.bookmyshow.main.model.Crew;
import com.bookmyshow.main.model.DateFilter;
import com.bookmyshow.main.model.Event;
import com.bookmyshow.main.model.Format;
import com.bookmyshow.main.model.Genres;
import com.bookmyshow.main.model.Languages;
import com.bookmyshow.main.model.MoreFilters;
import com.bookmyshow.main.model.Price;
import com.bookmyshow.main.model.ReleaseMonth;
import com.bookmyshow.main.model.Tag;
import com.bookmyshow.main.model.Venue;
import com.bookmyshow.main.repository.CastRepository;
import com.bookmyshow.main.repository.CategoriesRepository;
import com.bookmyshow.main.repository.CityRepository;
import com.bookmyshow.main.repository.CrewRepository;
import com.bookmyshow.main.repository.DateFilterRepository;
import com.bookmyshow.main.repository.EventRepository;
import com.bookmyshow.main.repository.FormatRepository;
import com.bookmyshow.main.repository.GenresRepository;
import com.bookmyshow.main.repository.LanguagesRepository;
import com.bookmyshow.main.repository.MoreFiltersRepository;
import com.bookmyshow.main.repository.PriceRepository;
import com.bookmyshow.main.repository.ReleaseMonthRepository;
import com.bookmyshow.main.repository.VenueRepository;
import com.bookmyshow.main.specification.EventSpecification;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock private EventRepository eventRepository;
    @Mock private LanguagesRepository languagesRepository;
    @Mock private GenresRepository genresRepository;
    @Mock private FormatRepository formatRepository;
    @Mock private TagRepository tagRepository;
    @Mock private ReleaseMonthRepository releaseMonthRepository;
    @Mock private DateFilterRepository dateFilterRepository;
    @Mock private CategoriesRepository categoriesRepository;
    @Mock private MoreFiltersRepository moreFiltersRepository;
    @Mock private PriceRepository priceRepository;
    @Mock private CastRepository castRepository;
    @Mock private CrewRepository crewRepository;
    @Mock private CityRepository cityRepository;
    @Mock private VenueRepository venueRepository;
    @Mock private ApplicationEventPublisher eventPublisher;
    @Mock private ModelMapper mapper;

    @InjectMocks private EventServiceImpl eventService;

    private Event event;
    private EventDTO eventDto;

    @BeforeEach
    void setUp() {
        event = new Event();
        event.setEventId(1L);
        event.setName("Test Event");
        event.setDescription("Desc");
        event.setRunTime("120min");
        event.setStartDate(LocalDate.of(2025, 9, 8));
        event.setEndDate(LocalDate.of(2025, 9, 10));
        event.setEventType("Movie");
        event.setImageurl("imageUrl");
        event.setImdbRating(8.5);
        event.setLikes(100.0);
        event.setVotes(50.0);
        event.setCurrentlyPlaying(true);
        event.setDeleted(false);
        event.setAgeLimit(13);
        event.setReleasingOn(LocalDate.of(2025, 9, 8));
        event.setLanguages(new ArrayList<>());
        event.setGenres(new ArrayList<>());
        event.setFormat(new ArrayList<>());
        event.setTag(new ArrayList<>());
        event.setReleaseMonth(new ArrayList<>());
        event.setDateFilter(new ArrayList<>());
        event.setCategories(new ArrayList<>());
        event.setMoreFilters(new ArrayList<>());
        event.setPrice(new ArrayList<>());
        event.setCast(new ArrayList<>());
        event.setCrew(new ArrayList<>());
        event.setCity(new ArrayList<>());

        eventDto = new EventDTO();
        eventDto.setEventId(1L);
        eventDto.setName("Test Event");
        eventDto.setDescription("Desc");
        eventDto.setRunTime("120");
        eventDto.setStartDate(LocalDate.of(2025, 9, 8));
        eventDto.setEndDate(LocalDate.of(2025, 9, 10));
        eventDto.setEventType("Movie");
        eventDto.setImageurl("imageUrl");
        eventDto.setImdbRating(8.5);
        eventDto.setLikes(100.0);
        eventDto.setVotes(50.0);
        eventDto.setCurrentlyPlaying(true);
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

    // ------------------- Create Event Tests -------------------
    @Test
    void testCreateEvent_basic() throws IOException {
        MockMultipartFile poster = new MockMultipartFile("poster", "poster.jpg", "image/jpeg", "dummy".getBytes());

        when(mapper.map(any(EventDTO.class), eq(Event.class))).thenReturn(event);
        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(languagesRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
        when(genresRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
        when(formatRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
        when(tagRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
        when(releaseMonthRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
        when(dateFilterRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
        when(categoriesRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
        when(moreFiltersRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
        lenient().when(castRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
        when(cityRepository.findAllById(anyList())).thenReturn(Collections.emptyList());

        EventDTO result = eventService.createEvent(eventDto, poster, null, null);

        assertNotNull(result);
        assertEquals("Test Event", result.getName());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void testCreateEvent_withCastCrewVenues() throws IOException {
        MockMultipartFile poster = new MockMultipartFile("poster", "poster.jpg", "image/jpeg", "poster".getBytes());
        MockMultipartFile castImage = new MockMultipartFile("cast", "cast1.jpg", "image/jpeg", "cast".getBytes());
        MockMultipartFile crewImage = new MockMultipartFile("crew", "crew1.jpg", "image/jpeg", "crew".getBytes());

        CastDTO castDTO = new CastDTO();
        castDTO.setActorName("Actor Name");
        eventDto.setCast(List.of(castDTO));

        CrewDTO crewDTO = new CrewDTO();
        crewDTO.setMemberName("Crew Member");
        eventDto.setCrew(List.of(crewDTO));

        eventDto.setVenue(List.of(10));
        eventDto.setAgeLimit(18);

        Venue venue = new Venue();
        venue.setId(10L);

        when(castRepository.findByActorName("Actor Name")).thenReturn(Optional.empty());
        when(castRepository.save(any(Cast.class))).thenAnswer(inv -> inv.getArgument(0));

        when(crewRepository.findByMemberName("Crew Member")).thenReturn(Optional.empty());
        when(crewRepository.save(any(Crew.class))).thenAnswer(inv -> inv.getArgument(0));

        when(venueRepository.findAllById(anyList())).thenReturn(List.of(venue));
        when(mapper.map(any(EventDTO.class), eq(Event.class))).thenReturn(new Event());
        when(eventRepository.save(any(Event.class))).thenAnswer(inv -> {
            Event e = inv.getArgument(0);
            e.setEventId(1L);
            e.setAgeLimit(18);
            return e;
        });

        EventDTO result = eventService.createEvent(eventDto, poster, List.of(castImage), List.of(crewImage));

        assertNotNull(result);
        verify(castRepository).save(argThat(cast -> cast.getActorName().equals("Actor Name") && cast.getCastImg() != null));
        verify(crewRepository).save(argThat(crew -> crew.getMemberName().equals("Crew Member") && crew.getCrewImg() != null));
        verify(venueRepository).findAllById(anyList());
        verify(eventRepository).save(any(Event.class));
    }

    // ------------------- Get Event Tests -------------------
    @Test
    void testGetEventById_found() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        EventResponseDto result = eventService.getEventById(1L);

        assertNotNull(result);
        assertEquals("Test Event", result.getName());
    }

    @Test
    void testGetEventById_notFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        EventCustomException ex = assertThrows(EventCustomException.class, () -> eventService.getEventById(1L));
        assertEquals("Event not found with id: 1", ex.getMessage());
    }

    // ------------------- Update Event Tests -------------------
    @Test
    void testGetAllLanguages_ByEventType() {
      
        String eventType = "conference";

       
        Languages language = new Languages();
        language.setLanguageId(1L);

        
        Event event = new Event();
        event.setLanguages(List.of(language)); 

       
        when(eventRepository.findByEventType(eventType)).thenReturn(List.of(event));

        LanguagesDTO languageDTO = new LanguagesDTO();
        when(mapper.map(language, LanguagesDTO.class)).thenReturn(languageDTO);

       
        List<LanguagesDTO> result = eventService.getAllLanguages(eventType);

       
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(languageDTO, result.get(0)); 
        verify(eventRepository).findByEventType(eventType);
        verify(mapper).map(language, LanguagesDTO.class);
    }


    @Test
    void testGetAllGenres_ByEventType() {
       
        String eventType = "music";

       
        Genres genre = new Genres();
        genre.setGenreId(1L); 

        
        Event event = new Event();
        event.setGenres(List.of(genre)); 
        
        when(eventRepository.findByEventType(eventType)).thenReturn(List.of(event));

        
        GenresDTO genreDTO = new GenresDTO();
        when(mapper.map(genre, GenresDTO.class)).thenReturn(genreDTO);

        
        List<GenresDTO> result = eventService.getAllGenres(eventType);

        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(genreDTO, result.get(0));

        verify(eventRepository).findByEventType(eventType);
        verify(mapper).map(genre, GenresDTO.class);
    }


    @Test
    void testGetAllFormats() {
        Format format = new Format();
        format.setFormatId(1L);
        when(formatRepository.findAll()).thenReturn(List.of(format));
        FormatDTO formatDto = new FormatDTO();
        when(mapper.map(format, FormatDTO.class)).thenReturn(formatDto);

        List<FormatDTO> result = eventService.getAllFormats();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(formatRepository).findAll();
    }

    @Test
    void testGetAllTags() {
        Tag tag = new Tag();
        tag.setTagId(1L);
        when(tagRepository.findAll()).thenReturn(List.of(tag));
        TagDTO tagDto = new TagDTO();
        when(mapper.map(tag, TagDTO.class)).thenReturn(tagDto);

        List<TagDTO> result = eventService.getAllTags();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tagRepository).findAll();
    }
  



    @Test
    void testGetAllReleaseMonths() {
        ReleaseMonth month = new ReleaseMonth();
        month.setReleaseMonthId(1L);
        when(releaseMonthRepository.findAll()).thenReturn(List.of(month));
        ReleaseMonthDTO monthDto = new ReleaseMonthDTO();
        when(mapper.map(month, ReleaseMonthDTO.class)).thenReturn(monthDto);

        List<ReleaseMonthDTO> result = eventService.getAllReleaseMonths();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(releaseMonthRepository).findAll();
    }
    
    
    
    @Test
    void testGetAllCategories_Success() {
     
        String eventType = "CONCERT";

      
        ModelMapper testMapper = new ModelMapper();
        TypeMap<Categories, CategoryDTO> typeMap = testMapper.getTypeMap(Categories.class, CategoryDTO.class);
        if (typeMap == null) {
            typeMap = testMapper.createTypeMap(Categories.class, CategoryDTO.class);
        }
        typeMap.addMappings(mapper -> {
            mapper.map(Categories::getCategoryId, CategoryDTO::setCategoryId);
            mapper.map(Categories::getCategoriesName, CategoryDTO::setCategoryName);
        });

      
        Categories category1 = new Categories();
        category1.setCategoryId(1L);
        category1.setCategoriesName("Music");

        Categories category2 = new Categories();
        category2.setCategoryId(2L);
        category2.setCategoriesName("Art");

     
        Event event1 = new Event();
        event1.setCategories(List.of(category1));

        Event event2 = new Event();
        event2.setCategories(List.of(category2));

   
        when(eventRepository.findByEventType(eventType)).thenReturn(Arrays.asList(event1, event2));

      
        ReflectionTestUtils.setField(eventService, "mapper", testMapper);

      
        List<CategoryDTO> result = eventService.getAllCategories(eventType);

      
        assertEquals(2, result.size());

        List<Long> ids = result.stream().map(CategoryDTO::getCategoryId).collect(Collectors.toList());
        assertTrue(ids.containsAll(Arrays.asList(1L, 2L)));

        List<String> names = result.stream().map(CategoryDTO::getCategoryName).collect(Collectors.toList());
        assertTrue(names.containsAll(Arrays.asList("Music", "Art")));
    }

    
    
    @Test
    void testGetAllMoreFilters_Success() {
      
        String eventType = "CONCERT";

       
        ModelMapper testMapper = new ModelMapper();
        TypeMap<MoreFilters, MoreFilterDTO> typeMap = testMapper.getTypeMap(MoreFilters.class, MoreFilterDTO.class);
        if (typeMap == null) {
            typeMap = testMapper.createTypeMap(MoreFilters.class, MoreFilterDTO.class);
        }
        typeMap.addMappings(m -> {
            m.map(MoreFilters::getFilterId, MoreFilterDTO::setMoreFilterId);
            m.map(MoreFilters::getName, MoreFilterDTO::setMoreFilterName);
        });

   
        MoreFilters filter1 = new MoreFilters();
        filter1.setFilterId(1L);
        filter1.setName("VIP");

        MoreFilters filter2 = new MoreFilters();
        filter2.setFilterId(2L);
        filter2.setName("Backstage");

        Event event1 = new Event();
        event1.setMoreFilters(List.of(filter1));

        Event event2 = new Event();
        event2.setMoreFilters(List.of(filter2));

        when(eventRepository.findByEventType(eventType)).thenReturn(Arrays.asList(event1, event2));

     
        ReflectionTestUtils.setField(eventService, "mapper", testMapper);

       
        List<MoreFilterDTO> result = eventService.getAllMoreFilters(eventType);

        
        assertEquals(2, result.size());

        List<Long> ids = result.stream().map(MoreFilterDTO::getMoreFilterId).collect(Collectors.toList());
        assertTrue(ids.containsAll(Arrays.asList(1L, 2L)));
    }

    
    @Test
    void testGetAllDateFilters() {
        DateFilter filter = new DateFilter();
        filter.setDateFilterId(1L);
        when(dateFilterRepository.findAll()).thenReturn(List.of(filter));
        DateFilterDTO filterDto = new DateFilterDTO();
        when(mapper.map(filter, DateFilterDTO.class)).thenReturn(filterDto);

        List<DateFilterDTO> result = eventService.getAllDateFilters();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(dateFilterRepository).findAll();
    }

    



    @Test
    void testGetAllPrice() {
        Price price = new Price();
        price.setPriceId(1L);
        when(priceRepository.findAll()).thenReturn(List.of(price));
        PriceDTO priceDto = new PriceDTO();
        when(mapper.map(price, PriceDTO.class)).thenReturn(priceDto);

        List<PriceDTO> result = eventService.getAllPrices();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(priceRepository).findAll();
    }
    @Test
    void testFilterEvents() throws Exception {
       
        String type = "Movie";
        List<Integer> languages = List.of(1, 2);
        List<Integer> genres = List.of(1, 2);
        List<Integer> formats = List.of(1, 2);
        List<Integer> tags = List.of(1, 2);
        List<Integer> categories = List.of(1, 2);
        List<Integer> price = List.of(1, 2);
        List<Integer> moreFilters = List.of(1, 2);
        List<Integer> releaseMonths = List.of(1, 2);
        List<Integer> dateFilters = List.of(1, 2);

      
        int page = 0;  
        int size = 10; 

     
        Event event1 = new Event();
        event1.setDeleted(false);
        event1.setAgeLimit(16);
        event1.setName("Sample Movie");

        Event event2 = new Event();
        event2.setDeleted(true);  
        event2.setAgeLimit(16);
        event2.setName("Deleted Movie");

     
        List<Event> events = List.of(event1, event2);

   
        Specification<Event> mockSpec = Mockito.mock(Specification.class);

      
        try (MockedStatic<EventSpecification> mockedStatic = Mockito.mockStatic(EventSpecification.class)) {
            mockedStatic.when(() -> EventSpecification.filterEvents(
                    type, languages, genres, formats, tags, categories, price, moreFilters, releaseMonths, dateFilters
            )).thenReturn(mockSpec);

         
            when(eventRepository.findAll(mockSpec, PageRequest.of(page, size))).thenReturn(new PageImpl<>(events, PageRequest.of(page, size), events.size()));

         
            Page<EventResponseDtoCard> result = eventService.filterEvents(
                    type, languages, genres, formats, tags, categories, price, moreFilters, releaseMonths, dateFilters, page, size
            );

        
            assertEquals(1, result.getContent().size());
            assertEquals("Sample Movie", result.getContent().get(0).getName());

          
            verify(eventRepository).findAll(mockSpec, PageRequest.of(page, size));
        }
    }

    @Test
    void testSearchEventNames_WithEventTypes() {
        String name = "concert";
        List<String> eventTypes = List.of("Music", "Festival");

      
        Event event1 = new Event();
        event1.setEventId(101L);
        event1.setName("Music Concert");

        Event event2 = new Event();
        event2.setEventId(102L);
        event2.setName("Festival Night");

        List<Event> mockEvents = List.of(event1, event2);

       
        when(eventRepository.searchByNameAndEventTypes(eq(name), anyList()))
            .thenReturn(mockEvents);

       
        List<EventSearchDTO> result = eventService.searchEventNames(name, eventTypes);

      
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(101L, result.get(0).getEventId());
        assertEquals("Music Concert", result.get(0).getName());
        assertEquals(102L, result.get(1).getEventId());
        assertEquals("Festival Night", result.get(1).getName());

        
        verify(eventRepository).searchByNameAndEventTypes(eq(name), eq(List.of("music", "festival")));
    }

    @Test
    void testSearchEventNames_WithoutEventTypes() {
        String name = "concert";
        List<String> eventTypes = Collections.emptyList();

        Event event = new Event();
        event.setEventId(201L);
        event.setName("Solo Concert");

        when(eventRepository.searchByNameOnly(name)).thenReturn(List.of(event));

        List<EventSearchDTO> result = eventService.searchEventNames(name, eventTypes);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(201L, result.get(0).getEventId());
        assertEquals("Solo Concert", result.get(0).getName());

        verify(eventRepository).searchByNameOnly(name);
    }

    @Test
    void testSearchEventNames_NullEventTypes() {
        String name = "concert";

        Event event = new Event();
        event.setEventId(301L);
        event.setName("Live Concert");

        when(eventRepository.searchByNameOnly(name)).thenReturn(List.of(event));

        List<EventSearchDTO> result = eventService.searchEventNames(name, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(301L, result.get(0).getEventId());
        assertEquals("Live Concert", result.get(0).getName());

        verify(eventRepository).searchByNameOnly(name);
    }

    @Test
    void testGetPopularEvents_WithEventType() {
        String eventType = "sports";

        Event event = new Event();
        event.setEventId(1L);
        event.setDeleted(false);
        event.setName("Sports Gala");
        event.setAgeLimit(16);

        List<Event> mockEvents = List.of(event);

        when(eventRepository.findTop10ByEventTypeOrderByReleasingOnDesc(eventType))
            .thenReturn(mockEvents);

        EventResponseDtoCard dto = new EventResponseDtoCard();


        List<EventResponseDtoCard> result = eventService.getPopularEvents(eventType);

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(eventRepository).findTop10ByEventTypeOrderByReleasingOnDesc(eventType);

    }

    @Test
    void testGetPopularEvents_WithoutEventType() {
        Event event = new Event();
        event.setEventId(2L);
        event.setDeleted(false);
        event.setName("Open Festival");
        event.setAgeLimit(16);

        when(eventRepository.findTop10ByOrderByReleasingOnDesc())
            .thenReturn(List.of(event));

        EventResponseDtoCard dto = new EventResponseDtoCard();


        List<EventResponseDtoCard> result = eventService.getPopularEvents("");

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(eventRepository).findTop10ByOrderByReleasingOnDesc();

    }

    @Test
    void testGetPopularEvents_ThrowsExceptionWhenNoEvents() {
        when(eventRepository.findTop10ByOrderByReleasingOnDesc())
            .thenReturn(Collections.emptyList());

        EventCustomException exception = assertThrows(EventCustomException.class, () -> {
            eventService.getPopularEvents("");
        });

        assertEquals("No Events found", exception.getMessage());
        verify(eventRepository).findTop10ByOrderByReleasingOnDesc();
    }
    
    @Test
    public void testUpdateEvent_success() throws IOException {
        // Given
        Long eventId = 1L;
        Event existingEvent = new Event();
        existingEvent.setEventId(eventId);
        existingEvent.setName("Old Event");

        EventDTO dto = new EventDTO();
        dto.setName("New Event");
        dto.setDescription("Updated description");
        dto.setAgeLimit(16);

        MockMultipartFile poster = new MockMultipartFile("poster", "poster.jpg", "image/jpeg", "poster".getBytes());
        CastDTO castDTO = new CastDTO(); castDTO.setActorName("New Actor"); dto.setCast(List.of(castDTO));
        CrewDTO crewDTO = new CrewDTO(); crewDTO.setMemberName("New Crew"); dto.setCrew(List.of(crewDTO));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(castRepository.findByActorName(anyString())).thenReturn(Optional.empty());
        when(castRepository.save(any(Cast.class))).thenAnswer(inv -> inv.getArgument(0));
        when(crewRepository.findByMemberName(anyString())).thenReturn(Optional.empty());
        when(crewRepository.save(any(Crew.class))).thenAnswer(inv -> inv.getArgument(0));
        when(eventRepository.save(any(Event.class))).thenAnswer(inv -> inv.getArgument(0));

        EventDTO result = eventService.updateEvent(eventId, dto, poster, List.of(poster), List.of(poster));

        assertNotNull(result);
        assertEquals("New Event", result.getName());
        verify(eventRepository).findById(eventId);
        verify(eventRepository).save(any(Event.class));
        verify(castRepository, times(2)).save(any(Cast.class));
        verify(crewRepository, times(2)).save(any(Crew.class));
    }

    // ------------------- Delete Event -------------------
    @Test
    void testDeleteEvent_success() {
        Long eventId = 1L;
        event.setDeleted(false);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        eventService.deleteEvent(eventId);

        verify(eventRepository).save(event);
    }

    // ------------------- Search Event Tests -------------------
    @Test
    void testSearchEventNames_withEventTypes() {
        String name = "concert";
        List<String> eventTypes = List.of("Music", "Festival");

        Event e1 = new Event(); e1.setEventId(101L); e1.setName("Music Concert");
        Event e2 = new Event(); e2.setEventId(102L); e2.setName("Festival Night");

        when(eventRepository.searchByNameAndEventTypes(eq(name), anyList())).thenReturn(List.of(e1, e2));

        List<EventSearchDTO> result = eventService.searchEventNames(name, eventTypes);

        assertEquals(2, result.size());
        verify(eventRepository).searchByNameAndEventTypes(eq(name), eq(List.of("music", "festival")));
    }

    @Test
    void testSearchEventNames_withoutEventTypes() {
        String name = "concert";
        List<String> eventTypes = Collections.emptyList();

        Event e = new Event(); e.setEventId(201L); e.setName("Solo Concert");

        when(eventRepository.searchByNameOnly(name)).thenReturn(List.of(e));

        List<EventSearchDTO> result = eventService.searchEventNames(name, eventTypes);

        assertEquals(1, result.size());
        verify(eventRepository).searchByNameOnly(name);
    }

    // ------------------- Popular Events Tests -------------------
    @Test
    void testGetPopularEvents_withEventType() {
        Event e = new Event(); e.setEventId(1L); e.setDeleted(false); e.setName("Sports Gala"); e.setAgeLimit(16);
        when(eventRepository.findTop10ByEventTypeOrderByReleasingOnDesc("sports")).thenReturn(List.of(e));

        List<EventResponseDtoCard> result = eventService.getPopularEvents("sports");

        assertEquals(1, result.size());
        verify(eventRepository).findTop10ByEventTypeOrderByReleasingOnDesc("sports");
    }

    @Test
    void testGetPopularEvents_withoutEventType() {
        Event e = new Event(); e.setEventId(2L); e.setDeleted(false); e.setName("Open Festival"); e.setAgeLimit(16);
        when(eventRepository.findTop10ByOrderByReleasingOnDesc()).thenReturn(List.of(e));

        List<EventResponseDtoCard> result = eventService.getPopularEvents("");

        assertEquals(1, result.size());
        verify(eventRepository).findTop10ByOrderByReleasingOnDesc();
    }

    @Test
    void testGetPopularEvents_throwsExceptionIfNoEvents() {
        when(eventRepository.findTop10ByOrderByReleasingOnDesc()).thenReturn(Collections.emptyList());

        EventCustomException ex = assertThrows(EventCustomException.class, () -> eventService.getPopularEvents(""));
        assertEquals("No Events found", ex.getMessage());
    }

    // ------------------- Mapping Tests -------------------
    @Test
    void testToEventDto_fullMapping() {
        Venue venue = new Venue(); venue.setVenueName("Hall"); event.setVenues(List.of(venue));
        Languages lang = new Languages(); lang.setLanguageName("English"); event.setLanguages(List.of(lang));
        Genres genre = new Genres(); genre.setGenresName("Action"); event.setGenres(List.of(genre));
        Format format = new Format(); format.setFormatName("3D"); event.setFormat(List.of(format));
        Tag tag = new Tag(); tag.setTagName("Popular"); event.setTag(List.of(tag));
        ReleaseMonth month = new ReleaseMonth(); month.setReleaseMonthName("September"); event.setReleaseMonth(List.of(month));
        DateFilter df = new DateFilter(); df.setDateFilterName("This Week"); event.setDateFilter(List.of(df));
        Categories cat = new Categories(); cat.setCategoriesName("Music"); event.setCategories(List.of(cat));
        MoreFilters mf = new MoreFilters(); mf.setName("Indoor"); event.setMoreFilters(List.of(mf));
        Price price = new Price(); price.setPriceRange("₹200-₹500"); event.setPrice(List.of(price));
        Cast cast = new Cast(); cast.setActorName("John"); cast.setCastImg("j.jpg"); event.setCast(List.of(cast));
        Crew crew = new Crew(); crew.setMemberName("Jane"); crew.setCrewImg("jane.jpg"); event.setCrew(List.of(crew));
        City city = new City(); city.setName("Mumbai"); event.setCity(List.of(city));

        EventResponseDto dto = eventService.toEventdto(event);

        assertEquals("Test Event", dto.getName());
        assertEquals("Hall", dto.getVenueName().get(0));
        assertEquals("English", dto.getLanguages().get(0));
        assertEquals("Action", dto.getGenres().get(0));
        assertEquals("3D", dto.getFormat().get(0));
        assertEquals("Popular", dto.getTag().get(0));
        assertEquals("September", dto.getReleaseMonth().get(0));
        assertEquals("This Week", dto.getDateFilter().get(0));
        assertEquals("Music", dto.getCategories().get(0));
        assertEquals("Indoor", dto.getMoreFilters().get(0));
        assertEquals("₹200-₹500", dto.getPrice().get(0));
        assertEquals("John", dto.getCast().get(0).getActorName());
        assertEquals("j.jpg", dto.getCast().get(0).getCastImg());
        assertEquals("Jane", dto.getCrew().get(0).getMemberName());
        assertEquals("jane.jpg", dto.getCrew().get(0).getCrewImg());
        assertEquals("Mumbai", dto.getCity().get(0));
    }
}
