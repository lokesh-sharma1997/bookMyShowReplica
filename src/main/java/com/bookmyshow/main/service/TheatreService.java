package com.bookmyshow.main.service;

import com.bookmyshow.main.dto.TheatreDto;

import java.util.List;

public interface TheatreService {

    TheatreDto createTheatre(TheatreDto dto);

    List<TheatreDto> getAllTheatres();

    List<TheatreDto> getTheatresByName(String name);

    boolean deleteTheatre(Long id);
}
