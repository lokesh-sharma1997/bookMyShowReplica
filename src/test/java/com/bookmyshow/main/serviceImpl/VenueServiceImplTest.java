package com.bookmyshow.main.serviceImpl;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.bookmyshow.main.exception.VenueNotFoundException;
import com.bookmyshow.main.model.*;
import com.bookmyshow.main.repository.*;
import com.bookmyshow.main.dto.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VenueServiceImplTest {

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private AmenityRepository amenityRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private VenueServiceImpl venueService;

    @Test
    public void testCreateVenue_withNewCity_savesCityAndAddress() {
        VenueDTO dto = new VenueDTO();
        dto.setVenueName("Test Venue");
        dto.setVenueCapacity(100);
        dto.setVenueType("movie");

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Test Street");
        addressDTO.setPin("123456");

        CityVDTO cityVDTO = new CityVDTO();
        cityVDTO.setCityName("NewCity");
        addressDTO.setCity(cityVDTO);
        dto.setAddress(addressDTO);

        when(cityRepository.findByName("NewCity")).thenReturn(null);
        when(cityRepository.save(any(City.class))).thenAnswer(i -> {
            City city = i.getArgument(0);
            return city;
        });

        when(addressRepository.save(any(Address.class))).thenAnswer(i -> {
            Address address = i.getArgument(0);
            address.setId(20L);
            return address;
        });

        when(venueRepository.save(any(Venue.class))).thenAnswer(i -> {
            Venue v = i.getArgument(0);
            v.setId(30L);
            return v;
        });

        VenueDTO result = venueService.createVenue(dto);

        assertNotNull(result);
        assertEquals("Test Venue", result.getVenueName());
        assertEquals("NewCity", result.getAddress().getCity().getCityName());

        verify(cityRepository, times(1)).save(any(City.class));
        verify(addressRepository, times(1)).save(any(Address.class));
        verify(venueRepository, times(1)).save(any(Venue.class));
    }

    @Test
    public void testGetAllVenues_returnsNonDeletedVenues() {
        Venue venue1 = new Venue();
        venue1.setId(1L);
        venue1.setVenueName("Venue 1");
        venue1.setDeleted(false);

        Venue venue2 = new Venue();
        venue2.setId(2L);
        venue2.setVenueName("Venue 2");
        venue2.setDeleted(true);

        when(venueRepository.findAll()).thenReturn(Arrays.asList(venue1, venue2));

        List<VenueDTO> result = venueService.getAllVenues();

        assertEquals(1, result.size());
        assertEquals("Venue 1", result.get(0).getVenueName());
    }

    @Test
    public void testGetVenuesByCity_returnsVenues() {
        Venue venue = new Venue();
        venue.setId(1L);
        venue.setVenueName("City Venue");

        when(venueRepository.findByAddress_City_Name("TestCity")).thenReturn(Arrays.asList(venue));

        List<VenueDTO> result = venueService.getVenuesByCity("TestCity");

        assertEquals(1, result.size());
        assertEquals("City Venue", result.get(0).getVenueName());
    }

    @Test
    public void testGetVenuesByCity_noVenues_returnsEmptyList() {
        when(venueRepository.findByAddress_City_Name("UnknownCity")).thenReturn(null);

        List<VenueDTO> result = venueService.getVenuesByCity("UnknownCity");

        assertTrue(result.isEmpty());
    }

    @Test
    public void testSoftDeleteVenue_marksVenueDeleted() {
        Venue venue = new Venue();
        venue.setId(1L);
        venue.setDeleted(false);

        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));
        when(venueRepository.save(any(Venue.class))).thenAnswer(i -> i.getArgument(0));

        boolean result = venueService.softDeleteVenue(1L);

        assertTrue(result);
        assertTrue(venue.getDeleted());
        verify(venueRepository).save(venue);
    }

    @Test
    public void testSoftDeleteVenue_alreadyDeleted_throwsException() {
        Venue venue = new Venue();
        venue.setId(1L);
        venue.setDeleted(true);

        when(venueRepository.findById(1L)).thenReturn(Optional.of(venue));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            venueService.softDeleteVenue(1L);
        });

        assertEquals("Venue already deleted with id: 1", exception.getMessage());
    }

    @Test
    public void testSoftDeleteVenue_venueNotFound_throwsVenueNotFoundException() {
        when(venueRepository.findById(1L)).thenReturn(Optional.empty());

        VenueNotFoundException ex = assertThrows(VenueNotFoundException.class, () -> {
            venueService.softDeleteVenue(1L);
        });

        assertEquals("Venue not found with id: 1", ex.getMessage());
    }
}
