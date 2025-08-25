package com.bookmyshow.main.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Movie name is required")
    private String name;

  
    private List<String> language;

   
    private List<String> genre;

   
    private List<String> format;
    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Duration is required")
    private String duration;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String imageurl; 

    private Double rating;
    
    private Double likes;

    private Boolean currentlyPlaying;

    private Boolean deleted = false;

    
    
  
    

    
}
