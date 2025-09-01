package com.bookmyshow.main.serviceImpl;

import com.bookmyshow.main.dto.ScreenDto;
import com.bookmyshow.main.dto.SeatDto;
import com.bookmyshow.main.dto.VenueDto;
import com.bookmyshow.main.model.Screen;
import com.bookmyshow.main.model.Seat;
import com.bookmyshow.main.model.Venue;
import com.bookmyshow.main.repository.ScreenRepository;
import com.bookmyshow.main.repository.SeatRepository;
import com.bookmyshow.main.repository.VenueRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VenueServiceImplTest {

    private VenueRepository venueRepository;
    private ScreenRepository screenRepository;
    private SeatRepository seatRepository;
    private ModelMapper modelMapper;
    private VenueServiceImpl venueService;

    @BeforeEach
    void setUp() throws Exception {
        venueRepository = mock(VenueRepository.class);
        screenRepository = mock(ScreenRepository.class);
        seatRepository = mock(SeatRepository.class);
        modelMapper = new ModelMapper();
        venueService = new VenueServiceImpl();

        java.lang.reflect.Field venueRepoField = VenueServiceImpl.class.getDeclaredField("venueRepository");
        venueRepoField.setAccessible(true);
        venueRepoField.set(venueService, venueRepository);

        java.lang.reflect.Field screenRepoField = VenueServiceImpl.class.getDeclaredField("screenRepository");
        screenRepoField.setAccessible(true);
        screenRepoField.set(venueService, screenRepository);

        java.lang.reflect.Field seatRepoField = VenueServiceImpl.class.getDeclaredField("seatRepository");
        seatRepoField.setAccessible(true);
        seatRepoField.set(venueService, seatRepository);

        java.lang.reflect.Field mapperField = VenueServiceImpl.class.getDeclaredField("modelMapper");
        mapperField.setAccessible(true);
        mapperField.set(venueService, modelMapper);
    }

    @Test
    void testCreateVenue() {
        VenueDto dto = new VenueDto();
        dto.setName("Inox");
        dto.setLocation("Rajouri Garden");
        dto.setCity("Delhi");

        Venue venueEntity = modelMapper.map(dto, Venue.class);

        when(venueRepository.save(any(Venue.class))).thenReturn(venueEntity);

        VenueDto result = venueService.createVenue(dto);

        assertNotNull(result);
        assertEquals("Inox", result.getName());
        assertEquals("Delhi", result.getCity());
    }

    @Test
    void testGetAllVenues() {
        Venue v1 = new Venue(1L, "Inox", "Rajouri Garden", false, "Delhi");
        Venue v2 = new Venue(2L, "PVR", "Saket", false, "Delhi");

        when(venueRepository.findAll()).thenReturn(List.of(v1, v2));

        List<VenueDto> result = venueService.getAllVenues();

        assertEquals(2, result.size());
        assertEquals("PVR", result.get(1).getName());
    }

    @Test
    void testGetVenuesByCity_found() {
        Venue v1 = new Venue(1L, "Inox", "Some Location", false, "Delhi");
        Screen s1 = new Screen(1L, "Screen 1", 1L);
        Seat seat1 = new Seat(1L, "A", 1L, "Premium", 1L);

        when(venueRepository.findByCity("Delhi")).thenReturn(Collections.singletonList(v1));
        when(screenRepository.findByVenueId(1L)).thenReturn(Collections.singletonList(s1));
        when(seatRepository.findByScreenId(1L)).thenReturn(Collections.singletonList(seat1));

        List<VenueDto> result = venueService.getVenuesByCity("Delhi");

        assertEquals(1, result.size());
        VenueDto venueDto = result.get(0);
        assertEquals("Inox", venueDto.getName());
        assertEquals("Delhi", venueDto.getCity());
        assertEquals(1, venueDto.getScreens().size());

        ScreenDto screenDto = venueDto.getScreens().get(0);
        assertEquals("Screen 1", screenDto.getName());
        assertEquals(1, screenDto.getLayout().size());

        SeatDto seatDto = screenDto.getLayout().get(0);
        assertEquals("A", seatDto.getRow());
        assertEquals(1L, seatDto.getNumber());
        assertEquals("Premium", seatDto.getCategory());
    }

    @Test
    void testGetVenuesByCity_notFound() {
        when(venueRepository.findByCity("UnknownCity")).thenReturn(Collections.emptyList());

        List<VenueDto> result = venueService.getVenuesByCity("UnknownCity");

        assertTrue(result.isEmpty());
    }

    @Test
    void testSoftDeleteVenue_success() {
        Venue venue = new Venue(1L, "PVR", "Vikaspuri", false, "Delhi");

        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));
        when(venueRepository.save(any(Venue.class))).thenReturn(venue);

        boolean result = venueService.softDeleteVenue(1L);

        assertTrue(result);
        assertTrue(venue.getDeleted());
        verify(venueRepository, times(1)).save(venue);
    }

    @Test
    void testSoftDeleteVenue_notFound() {
        when(venueRepository.findById(99L)).thenReturn(Optional.empty());

        boolean result = venueService.softDeleteVenue(99L);

        assertFalse(result);
        verify(venueRepository, never()).save(any());
    }
}
