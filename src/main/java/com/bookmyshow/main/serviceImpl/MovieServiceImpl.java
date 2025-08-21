package com.bookmyshow.main.serviceImpl;


import com.bookmyshow.main.dto.MovieDto;
import com.bookmyshow.main.model.Movie;
import com.bookmyshow.main.repository.MovieRepository;
import com.bookmyshow.main.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
	@Autowired
	private ObjectMapper objectMapper;
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ModelMapper mapper;

    private MovieDto toDto(Movie movie) { return mapper.map(movie, MovieDto.class); }
    private Movie toEntity(MovieDto dto) { return mapper.map(dto, Movie.class); }

    
    
//    @Override
//    public MovieDto createMovie(MovieDto movieDto, MultipartFile poster) throws IOException {
//        String base64Image = Base64.getEncoder().encodeToString(poster.getBytes());
//        Movie movie = toEntity(movieDto);
//        movie.setImageurl(base64Image);
//        return toDto(movieRepository.save(movie));
//    }
//
    @Override
    public MovieDto createMovie(MovieDto movieDto, MultipartFile poster) throws IOException {
        
        String fileName = UUID.randomUUID() + "_" + poster.getOriginalFilename();
        Path path = Paths.get("uploads/" + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, poster.getBytes());

    
        movieDto.setImageurl("/uploads/" + fileName);

        Movie movie = toEntity(movieDto);
        return toDto(movieRepository.save(movie));
    }

    @Override
    public MovieDto getMovieById(Long id) {
    	System.out.println("DEBUG: fetching movie id = " + id);
        return movieRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    @Override
    public MovieDto getMovieByName(String name) {
        return movieRepository.findByName(name)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Movie not found with name: " + name));
    }


    @Override
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public MovieDto updateMovie(Long id, MovieDto movieDto) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        movie.setName(movieDto.getName());
        movie.setLanguage(movieDto.getLanguage());
        movie.setGenre(movieDto.getGenre());
        movie.setFormat(movieDto.getFormat());
        movie.setDescription(movieDto.getDescription());
        movie.setDuration(movieDto.getDuration());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setImageurl(movieDto.getImageurl());
        movie.setRating(movieDto.getRating());
        movie.setLikes(movieDto.getLikes());
        movie.setCurrentlyPlaying(movieDto.getCurrentlyPlaying());
        return toDto(movieRepository.save(movie));
    }

    @Override
    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        movie.setDeleted(true);
        movieRepository.save(movie);
    }
}
