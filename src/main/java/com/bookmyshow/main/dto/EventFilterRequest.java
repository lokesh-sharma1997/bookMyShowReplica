package com.bookmyshow.main.dto;

import java.util.List;

import lombok.Data;


@Data
public class EventFilterRequest {
    private List<String> languages;
    private List<String> genres;
    private List<String> formats;
    private String releaseMonth;

   
}
