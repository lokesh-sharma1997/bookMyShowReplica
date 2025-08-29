package com.bookmyshow.main.dto;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;
@Data
public class EventDto {
    private Long id;
    private String name;
    private List<String> language;

    private List<String> genre;

    private List<String> format;

    private String description;
    private String duration;
    private LocalDate releaseDate;
    private String imageurl;
    private Double rating;
    private Double likes;
    private Double votes;
    private Boolean currentlyPlaying;
    private String contentType;
    private List<CastDto> cast; 
    private String categoryType;
}
