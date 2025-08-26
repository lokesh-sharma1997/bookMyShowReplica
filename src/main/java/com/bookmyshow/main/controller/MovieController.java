package com.bookmyshow.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookmyshow.main.dto.MovieDto;
import com.bookmyshow.main.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/movies")
@Tag(name = "Movie Controller", description = "Manage movies in BookMyShow app")
public class MovieController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MovieService movieService;

    @Operation(summary = "Create a new movie", description = "Add a new movie with poster image")
    @ApiResponse(responseCode = "200", description = "Movie created successfully")
    @PostMapping(value="/cretemovie", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MovieDto> createMovie(
    		@RequestPart("movie") String movieJson,
            @RequestPart("poster") MultipartFile poster) throws IOException, java.io.IOException {

        MovieDto movieDto = objectMapper.readValue(movieJson, MovieDto.class);
        return ResponseEntity.ok(movieService.createMovie(movieDto, poster));
    }

    @Operation(summary = "Get movie by ID")
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id,@RequestParam(required = false) String contentType) {
        return ResponseEntity.ok(movieService.getMovieById(id,contentType));
    }

    @Operation(summary = "Get movie by name")
    @GetMapping("/by-name/{name}")
    public ResponseEntity<MovieDto> getMovieByName(@PathVariable String name,@RequestParam(required = false) String contentType) {
        return ResponseEntity.ok(movieService.getMovieByName(name,contentType));
    }

    @Operation(summary = "Get all movies")
    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies(@RequestParam(required = false) String contentType) {
        return ResponseEntity.ok(movieService.getAllMovies(contentType));
    }

    @Operation(summary = "Update a movie")
    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id, @RequestBody MovieDto movieDto) {
        return ResponseEntity.ok(movieService.updateMovie(id, movieDto));
    }

    @Operation(summary = "Delete a movie")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "movie filter")
    @GetMapping("/filter")
    public List<MovieDto> filterMovies(
            @RequestParam(required = false) List<String> languages,
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) List<String> formats,
            @RequestParam(required = false) String releaseMonth
    ) {
        return movieService.filterMovies(languages, genres, formats, releaseMonth);
    }
    @Operation(summary = "Get All languages")
    @GetMapping("/languages")
    public ResponseEntity<List<String>> getLanguages(@RequestParam(required = false) String contentType) {
        return ResponseEntity.ok(movieService.getAllLanguages(contentType));
    }
    @Operation(summary = "Get All Genres")
    @GetMapping("/genres")
    public ResponseEntity<List<String>> getGenres(@RequestParam(required = false) String contentType) {
        return ResponseEntity.ok(movieService.getAllGenres(contentType));
    }
    @Operation(summary = "Get All Formats")
    @GetMapping("/formats")
    public ResponseEntity<List<String>> getFormats(@RequestParam(required = false) String contentType) {
        return ResponseEntity.ok(movieService.getAllFormats(contentType));
    }

}
