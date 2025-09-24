package com.bookmyshow.main.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowRequestDTO {
	
	@JsonIgnore
    private Long showId;
    private String eventType;
    private Long eventId;
    private Long venueId;
    private String city;
    private LocalDate date;
    private LocalTime startTime;
    private Integer duration;
    private List<String> languageName;  
    private String status;
    private String format;
    private String screenName;
    private List<Integer> showPrice;
    private List<String> layoutName; 
    private List<ReserveSeatDTO> reserveSeat;
    
}



