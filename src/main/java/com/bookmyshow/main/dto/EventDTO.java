package com.bookmyshow.main.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

// Master DTO
@Data
public class EventDTO {
    private Long eventId;
    private String name;
    private String description;
    private String runTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private String eventType;
    private String imageurl;
    private Double imdbRating;
    private Double likes;
    private Double votes;
    private Boolean currentlyPlaying;
    private Boolean deleted;
    private int ageLimit;
    private LocalDate releasingOn;

    // Nested DTOs
    private List<Integer> languages;
    private List<Integer> genres;
    private List<Integer> format;
    private List<Integer> tag;
    private List<Integer> releaseMonth;
    private List<Integer> dateFilter;
    private List<Integer> categories;
    private List<Integer> moreFilters;
    private List<Integer> price;
    private List<CastDTO> cast;
    private List<CrewDTO> crew;
    private List<Integer> city;
}
