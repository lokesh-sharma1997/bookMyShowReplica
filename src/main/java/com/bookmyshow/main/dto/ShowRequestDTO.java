package com.bookmyshow.main.dto;

import java.util.List;

import com.bookmyshow.main.model.Languages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowRequestDTO {
    private Long showId;
    private String eventType;
    private Long eventId;
    private Long venueId;
    private String city;
    private String date;
    private String startTime;
    private Integer duration;
    private List<String> languageName;  // <- updated to LanguagesDTO
    private String status;
    private List<String> format;
    private String screenName;
    private List<Integer> showPrice;
    private List<SupportedCategoryDTO> supportedCategories;
    private List<ReserveSeatDTO> reserveSeat;
    
}



