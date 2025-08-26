package com.bookmyshow.main.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bookmyshow.main.dto.MovieDto;

public interface MovieService {
    MovieDto createMovie(MovieDto movieDto, MultipartFile poster) throws IOException;
    MovieDto getMovieById(Long id,String contentType);
    MovieDto getMovieByName(String name,String contentType);
    List<MovieDto> getAllMovies(String contentType);
    MovieDto updateMovie(Long id, MovieDto movieDto);
    void deleteMovie(Long id);
    List<MovieDto> filterMovies(  List<String> languages,
            List<String> genres,
            List<String> formats,
            String releaseMonth);
    List<String> getAllLanguages(String contentType);
    List<String> getAllGenres(String contentType);
    List<String> getAllFormats(String contentType);
	
}

