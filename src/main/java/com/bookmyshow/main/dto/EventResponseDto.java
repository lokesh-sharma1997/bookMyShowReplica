package com.bookmyshow.main.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
@Data
public class EventResponseDto {
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
    private List<String> languages;
    private List<String> genres;
    private List<String> format;
    private List<String> tag;
    private List<String> releaseMonth;
    private List<String> dateFilter;
    private List<String> categories;
    private List<String> moreFilters;
    private List<String> price;
    private List<CastDTO> cast;
    private List<CrewDTO> crew;
    private List<String> city;
	
}
