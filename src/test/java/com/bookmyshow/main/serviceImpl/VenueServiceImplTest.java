package com.bookmyshow.main.service;

import com.bookmyshow.main.dto.TheatreDto;
import com.bookmyshow.main.model.Theatre;
import com.bookmyshow.main.repository.TheatreRepository;
import com.bookmyshow.main.serviceImpl.TheatreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TheatreServiceImplTest {

    private TheatreRepository theatreRepository;
    private ModelMapper modelMapper;
    private TheatreServiceImpl theatreService;

    @BeforeEach
    void setUp() throws Exception {
        theatreRepository = mock(TheatreRepository.class);
        modelMapper = new ModelMapper();
        theatreService = new TheatreServiceImpl();

        Field repoField = TheatreServiceImpl.class.getDeclaredField("theatreRepository");
        repoField.setAccessible(true);
        repoField.set(theatreService, theatreRepository);

        Field mapperField = TheatreServiceImpl.class.getDeclaredField("modelMapper");
        mapperField.setAccessible(true);
        mapperField.set(theatreService, modelMapper);
    }

    @Test
    void testCreateTheatre() {
        TheatreDto dto = new TheatreDto("Inox", "Rajouri Garden", "Delhi");

        Theatre entity = modelMapper.map(dto, Theatre.class);
        entity.setId(1L);  

        when(theatreRepository.save(any(Theatre.class))).thenReturn(entity);

        TheatreDto result = theatreService.createTheatre(dto);

        assertNotNull(result);
        assertEquals("Inox", result.getName());
        assertEquals("Delhi", result.getCity());
    }

    @Test
    void testGetAllTheatres() {
        Theatre t1 = new Theatre(1L, "Inox", "Rajouri", false, "Delhi");
        Theatre t2 = new Theatre(2L, "PVR", "Saket", false, "Delhi");

        when(theatreRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

        List<TheatreDto> result = theatreService.getAllTheatres();

        assertEquals(2, result.size());
        assertEquals("PVR", result.get(1).getName());
    }

    @Test
    void testGetTheatresByName_found() {
        Theatre t1 = new Theatre(1L, "Inox", "CP", false, "Delhi");

        when(theatreRepository.findBynameIgnoreCase("inox"))
                .thenReturn(Collections.singletonList(t1));

        List<TheatreDto> result = theatreService.getTheatresByName("inox");

        assertEquals(1, result.size());
        assertEquals("Inox", result.get(0).getName());
    }

    @Test
    void testSoftDeleteTheatre_success() {
        Theatre theatre = new Theatre(1L, "PVR", "Vikaspuri", false, "Delhi");

        when(theatreRepository.findById(1L)).thenReturn(Optional.of(theatre));
        when(theatreRepository.save(any(Theatre.class))).thenReturn(theatre);

        boolean result = theatreService.softDeleteTheatre(1L);

        assertTrue(result);
        verify(theatreRepository, times(1)).save(theatre);
        assertTrue(theatre.getDeleted());
    }
    
    @Test
    void testSoftDeleteTheatre_notFound() {
        when(theatreRepository.findById(99L)).thenReturn(Optional.empty());

        boolean result = theatreService.softDeleteTheatre(99L);

        assertFalse(result);
        verify(theatreRepository, never()).save(any());
    }
}
    