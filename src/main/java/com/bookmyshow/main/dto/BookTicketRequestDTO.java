package com.bookmyshow.main.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

@Data
public class BookTicketRequestDTO {
    private Long userId;
    private Long eventId;
    private Long venueId;  
    private Long screenId;
    private Long showId;
    private LocalDate date;
    private LocalTime time;
    private List<String> reservedSeats;
}
