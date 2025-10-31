package com.bookmyshow.main.dto;

import java.time.LocalDate;
import java.util.List;

import com.bookmyshow.main.config.ValidEndDate;

import jakarta.validation.constraints.FutureOrPresent;
//import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;

// Master DTO

@Data
@ValidEndDate
public class EventDTO {
    private Long eventId;
    private Long adminId;
    private String name;
    private String description;
    private String runTime;
    @FutureOrPresent(message = "start date must be today or in the future")
    private LocalDate startDate;
    private LocalDate endDate;
    private String eventType;
    private String imageurl;
    private Double imdbRating;
    private Double likes;
    private Double votes;
    private Boolean currentlyPlaying;
    private int ageLimit;
    @FutureOrPresent(message = "ReleasingOn must be today or in the future")
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
    private List<CastDTO> cast;
    private List<CrewDTO> crew;
    private List<Integer> city;
    private List<Integer> venue;
    private List<ShowDTO> show;
}