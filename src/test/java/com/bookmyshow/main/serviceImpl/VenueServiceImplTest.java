//
//package com.bookmyshow.main.serviceImpl;
//
//import com.bookmyshow.main.dto.VenueDto;
//import com.bookmyshow.main.model.Venue;
//import com.bookmyshow.main.repository.VenueRepository;
//import com.bookmyshow.main.serviceImpl.VenueServiceImpl;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class VenueServiceImplTest {
//
//    private VenueRepository venueRepository;
//    private ModelMapper modelMapper;
//    private VenueServiceImpl venueService;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        venueRepository = mock(VenueRepository.class);
//        modelMapper = new ModelMapper();
//        venueService = new VenueServiceImpl();
//
//        java.lang.reflect.Field repoField = VenueServiceImpl.class.getDeclaredField("venueRepository");
//        repoField.setAccessible(true);
//        repoField.set(venueService, venueRepository);
//
//        java.lang.reflect.Field mapperField = VenueServiceImpl.class.getDeclaredField("modelMapper");
//        mapperField.setAccessible(true);
//        mapperField.set(venueService, modelMapper);
//    }
//
//    @Test
//    void testCreateVenue() {
//        VenueDto dto = new VenueDto("Inox", "Rajouri Garden", "Delhi");
//        Venue venueEntity = modelMapper.map(dto, Venue.class);
//
//        when(venueRepository.save(any(Venue.class))).thenReturn(venueEntity);
//
//        VenueDto result = venueService.createVenue(dto);
//
//        assertNotNull(result);
//        assertEquals("Inox", result.getName());
//        assertEquals("Delhi", result.getCity());
//    }
//
//    @Test
//    void testGetAllVenues() {
//        Venue v1 = new Venue(1L, "Inox", "Rajouri Garden", false, "Delhi");
//        Venue v2 = new Venue(2L, "PVR", "Saket", false, "Delhi");
//
//        when(venueRepository.findAll()).thenReturn(Arrays.asList(v1, v2));
//
//        List<VenueDto> result = venueService.getAllVenues();
//
//        assertEquals(2, result.size());
//        assertEquals("PVR", result.get(1).getName());
//    }
//
//    @Test
//    void testGetVenuesByName_found() {
//        Venue v1 = new Venue(1L, "Inox", "CP", false, "Delhi");
//
//        when(venueRepository.findBynameIgnoreCase("inox"))
//                .thenReturn(Collections.singletonList(v1));
//
//        List<VenueDto> result = venueService.getVenuesByName("inox");
//
//        assertEquals(1, result.size());
//        assertEquals("Inox", result.get(0).getName());
//    }
//
//    @Test
//    void testGetVenuesByName_notFound() {
//        when(venueRepository.findBynameIgnoreCase("abc"))
//                .thenReturn(Collections.emptyList());
//
//        List<VenueDto> result = venueService.getVenuesByName("abc");
//
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void testSoftDeleteVenue_success() {
//        Venue venue = new Venue(1L, "PVR", "Vikaspuri", false, "Delhi");
//
//        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));
//        when(venueRepository.save(any(Venue.class))).thenReturn(venue);
//
//        boolean result = venueService.softDeleteVenue(1L);
//
//        assertTrue(result);
//        assertTrue(venue.getDeleted());
//        verify(venueRepository, times(1)).save(venue);
//    }
//
//    @Test
//    void testSoftDeleteVenue_notFound() {
//        when(venueRepository.findById(99L)).thenReturn(Optional.empty());
//
//        boolean result = venueService.softDeleteVenue(99L);
//
//        assertFalse(result);
//        verify(venueRepository, never()).save(any());
//    }
//}
