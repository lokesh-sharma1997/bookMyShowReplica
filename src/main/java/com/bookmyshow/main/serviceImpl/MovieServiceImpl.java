package com.bookmyshow.main.serviceImpl;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        String eventType = movieDto.getContentType();
        
        // Step 1: Predefined values
        Set<String> predefined = new HashSet<>(Arrays.asList(
                "Movie", "Show", "Cartoon", "Event"
        ));
     
        // Step 2: Agar predefined me hai to wahi use hoga
        if (predefined.contains(eventType)) {
            movie.setContentType(eventType);
        } else {
            // Step 3: New type ho to bhi save kar dena
            movie.setContentType(eventType);
        }
        return toDto(movieRepository.save(movie));
    }

    @Override
    public MovieDto getMovieById(Long id, String contentType) {
        System.out.println("DEBUG: fetching movie id = " + id);

        return movieRepository.findById(id)
                .filter(movie -> !movie.getDeleted() &&
                        (contentType == null || contentType.isEmpty() || contentType.equalsIgnoreCase(movie.getContentType())))
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }




    @Override
    public MovieDto getMovieByName(String name,String contentType) {
        Movie movie = movieRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        if (movie.getDeleted()) {
            throw new RuntimeException("Movie is deleted");
        }

        return toDto(movie);
    }



    @Override
    public List<MovieDto> getAllMovies(String contentType) {
        return movieRepository.findAll().stream()
                .filter(movie -> !movie.getDeleted() &&
                        (contentType == null || contentType.isEmpty() || contentType.equalsIgnoreCase(movie.getContentType())))
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
    
    public List<String> getAllLanguages(String contentType) {
        Set<String> languages = movieRepository.findByDeletedFalse()
                .stream()
                .filter(m -> contentType == null || contentType.isEmpty() ||
                contentType.equalsIgnoreCase(m.getContentType()))
                .filter(m -> m.getLanguage() != null)
                .flatMap(m -> m.getLanguage().stream())
                .collect(Collectors.toSet());
        List<String> sortedLanguages = new ArrayList<>(languages);
        Collections.sort(sortedLanguages);
        return sortedLanguages;
    }
 
    // Genres
    public List<String> getAllGenres(String contentType) {
        Set<String> genres = movieRepository.findByDeletedFalse()
                .stream()
                .filter(m -> contentType == null || contentType.isEmpty() ||
                contentType.equalsIgnoreCase(m.getContentType()))
                .filter(m -> m.getGenre() != null)
                .flatMap(m -> m.getGenre().stream())
                .collect(Collectors.toSet());
        List<String> sortedGenres = new ArrayList<>(genres);
        Collections.sort(sortedGenres);
        return sortedGenres;
    }
 
    // Formats
    public List<String> getAllFormats(String contentType) {
        Set<String> formats = movieRepository.findByDeletedFalse()
                .stream()
                .filter(m -> contentType == null || contentType.isEmpty() ||
                contentType.equalsIgnoreCase(m.getContentType()))
                .filter(m -> m.getFormat() != null)
                .flatMap(m -> m.getFormat().stream())
                .collect(Collectors.toSet());
        List<String> sortedFormats = new ArrayList<>(formats);
        Collections.sort(sortedFormats);
        return sortedFormats;
    }
 

}
