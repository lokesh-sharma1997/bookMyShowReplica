package com.bookmyshow.main.service;

import com.bookmyshow.main.dto.MovieDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    MovieDto createMovie(MovieDto movieDto, MultipartFile poster) throws IOException;
    MovieDto getMovieById(Long id);
    MovieDto getMovieByName(String name);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Long id, MovieDto movieDto);
    void deleteMovie(Long id);
}

