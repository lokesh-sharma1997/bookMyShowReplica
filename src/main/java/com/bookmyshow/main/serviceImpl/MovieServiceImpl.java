package com.bookmyshow.main.serviceImpl;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import java.util.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookmyshow.main.Specification.MovieSpecification;
import com.bookmyshow.main.dto.MovieDto;
import com.bookmyshow.main.model.Movie;
import com.bookmyshow.main.repository.MovieRepository;
import com.bookmyshow.main.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    
    
    @Override
    public MovieDto createMovie(MovieDto movieDto, MultipartFile poster) throws IOException {
        String base64Image = Base64.getEncoder().encodeToString(poster.getBytes());
        Movie movie = toEntity(movieDto);
        movie.setImageurl(base64Image);
        return toDto(movieRepository.save(movie));
    }

    @Override
    public MovieDto getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        if (movie.getDeleted()) {
            throw new RuntimeException("Movie is deleted");
        }

        return toDto(movie);
    }



    @Override
    public MovieDto getMovieByName(String name) {
        Movie movie = movieRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        if (movie.getDeleted()) {
            throw new RuntimeException("Movie is deleted");
        }

        return toDto(movie);
    }



    @Override
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream()
                .filter(movie -> !movie.getDeleted()) 
                .map(this::toDto)
                .collect(Collectors.toList());
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
    
    public List<MovieDto> filterMovies(
            List<String> languages,
            List<String> genres,
            List<String> formats,
            String releaseMonth
    ) {
        Specification<Movie> spec = MovieSpecification.filterMovies(languages, genres, formats, releaseMonth);
        return movieRepository.findAll(spec).stream()
                .filter(movie -> !movie.getDeleted())   
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
