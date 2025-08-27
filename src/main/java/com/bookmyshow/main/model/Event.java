package com.bookmyshow.main.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "movies")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Event name is required")
    private String name;

  
    @ElementCollection
    @CollectionTable(name = "Event_languages", joinColumns = @JoinColumn(name = "Event_id"))
    @Column(name = "language")
    private List<String> language;
 
    @ElementCollection
    @CollectionTable(name = "Event_genres", joinColumns = @JoinColumn(name = "Event_id"))
    @Column(name = "genre")
    private List<String> genre;
 
    @ElementCollection
    @CollectionTable(name = "Event_formats", joinColumns = @JoinColumn(name = "Event_id"))
    @Column(name = "format")
    private List<String> format;
    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Duration is required")
    private String duration;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;
    @NotBlank(message = "ContentType is required")
    private String contentType;
    
    @Column(columnDefinition = "TEXT")
    private String imageurl; 

    private Double rating;
    
    private Double likes;

    private Boolean currentlyPlaying;

    private Boolean deleted = false;

    @ElementCollection
    @CollectionTable(name = "Event_cast", joinColumns = @JoinColumn(name = "Event_id"))
    private List<Cast> cast;
    
   


    

    
}
