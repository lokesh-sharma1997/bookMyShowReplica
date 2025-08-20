package com.bookmyshow.main.controller;




import com.bookmyshow.main.dto.MovieDto;
import com.bookmyshow.main.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
	@Autowired
	private ObjectMapper objectMapper;
    @Autowired
    private MovieService movieService;

    @PostMapping(value="/cretemovie", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MovieDto> createMovie(
            @RequestPart("movie") String movieJson,
            @RequestPart("poster") MultipartFile poster) throws IOException {

       
        MovieDto movieDto = objectMapper.readValue(movieJson, MovieDto.class);

        return ResponseEntity.ok(movieService.createMovie(movieDto, poster));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<MovieDto> getMovieByName(@PathVariable String name) {
        return ResponseEntity.ok(movieService.getMovieByName(name));
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id, @RequestBody MovieDto movieDto) {
        return ResponseEntity.ok(movieService.updateMovie(id, movieDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}

